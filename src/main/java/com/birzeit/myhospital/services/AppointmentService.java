package com.birzeit.myhospital.services;

import com.birzeit.myhospital.dtos.appointment.AppointmentRequestDTO;
import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.appointment.DateTimeDTO;
import com.birzeit.myhospital.entities.Appointment;
import com.birzeit.myhospital.entities.AppointmentStatus;
import com.birzeit.myhospital.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService, DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    private Optional<Appointment> getAppointmentById(long id) {
        return appointmentRepository.findById(id);
    }

    public List<AppointmentResponseDTO> getAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentService::dtoMapper)
                .collect(Collectors.toList());
    }

    public AppointmentResponseDTO getAppointmentDetails(long id) {
        return dtoMapper(getAppointmentById(id).orElseThrow());
    }

    public void addAppointment(AppointmentRequestDTO appointmentRequest) {
        Appointment appointment = Appointment
                .builder()
                .date(LocalDateFormatter.makeDateFromString(appointmentRequest.date()))
                .time(LocalDateFormatter.makeTimeFromString(appointmentRequest.time()))
                .reason(appointmentRequest.reason())
                .patient(patientService.getPatientById(appointmentRequest.patientId()).orElseThrow())
                .doctor(doctorService.getDoctorById(appointmentRequest.doctorId()).orElseThrow())
                .appointmentStatus(AppointmentStatus.not_completed)
                .build();
        appointmentRepository.save(appointment);
    }

    public void deleteAppointmentById(long id) {
        try {
            appointmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new NoSuchElementException("No appointment exists with the given id");
        }
    }

    public void cancelAppointment(long id) {
        Optional<Appointment> appointment = getAppointmentById(id);
        if(appointment.isEmpty())
            throw new NoSuchElementException("No appointment exists with the given id");
        appointment.get().setAppointmentStatus(AppointmentStatus.canceled);
        appointmentRepository.save(appointment.get());
    }

    public void completeAppointment(long id) {
        Optional<Appointment> appointment = getAppointmentById(id);
        if(appointment.isEmpty())
            throw new NoSuchElementException("No appointment exists with the given id");
        appointment.get().setAppointmentStatus(AppointmentStatus.completed);
        appointmentRepository.save(appointment.get());
    }

    public void rescheduleAppointment(long id, DateTimeDTO newDateTimeDTO) throws IOException, NoSuchElementException{
        Optional<Appointment> appointment = getAppointmentById(id);
        if(appointment.isEmpty())
            throw new NoSuchElementException("No appointment exists with the given id");
        LocalDate newDate;
        LocalTime newTime;
        try {
            newDate = LocalDateFormatter.makeDateFromString(newDateTimeDTO.newDate());
            newTime = LocalDateFormatter.makeTimeFromString(newDateTimeDTO.newTime());
        } catch (Exception e) {
            throw new IOException("Please enter the date in the format \"yyyy/MM/dd\"" +
                    " and the time in the format \"HH:mm\"");
        }
        if(newDate.isBefore(LocalDate.now()) ||
                (LocalDate.now().isEqual(newDate) && newTime.isBefore(LocalTime.now())))
            throw new IOException("Date and time must be in the future");
        appointment.get().setDate(newDate);
        appointment.get().setTime(newTime);
        appointmentRepository.save(appointment.get());
    }

    public static AppointmentResponseDTO dtoMapper(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                PatientService.dtoMapper(appointment.getPatient()),
                DoctorService.dtoMapper(appointment.getDoctor()),
                LocalDateFormatter.formatDate(appointment.getDate()),
                LocalDateFormatter.formatTime(appointment.getTime()),
                appointment.getReason(),
                appointment.getAppointmentStatus());
    }

}
