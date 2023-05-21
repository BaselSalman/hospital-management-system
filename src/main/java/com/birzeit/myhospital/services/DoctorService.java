package com.birzeit.myhospital.services;

import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.doctor.DoctorRequestDTO;
import com.birzeit.myhospital.dtos.doctor.DoctorResponseDTO;
import com.birzeit.myhospital.entities.Appointment;
import com.birzeit.myhospital.entities.AppointmentStatus;
import com.birzeit.myhospital.entities.Doctor;
import com.birzeit.myhospital.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final MedicalServiceService medicalServiceService;

    DoctorService(DoctorRepository doctorRepository, MedicalServiceService medicalServiceService) {
        this.doctorRepository = doctorRepository;
        this.medicalServiceService = medicalServiceService;
    }

    public List<DoctorResponseDTO> getDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorService::dtoMapper)
                .collect(Collectors.toList());
    }

    public Optional<Doctor> getDoctorById(long id) {
        return doctorRepository.findById(id);
    }

    public void addDoctor(DoctorRequestDTO doctorRequest) {
        Doctor doctor = Doctor
                .builder()
                .name(doctorRequest.name())
                .email(doctorRequest.email())
                .phoneNumber(doctorRequest.phoneNumber())
                .medicalService(medicalServiceService.getMedicalServiceByName(doctorRequest.medicalServiceName()).orElseThrow())
                .build();
        doctorRepository.save(doctor);
    }

    public void deleteDoctorById(long id) {
        try {
            doctorRepository.deleteById(id);
        } catch (Exception e) {
            throw new NoSuchElementException("No doctor exists with the given id");
        }
    }

    public List<AppointmentResponseDTO> getUpcomingAppointments(long doctorId) {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if(doctor.isEmpty()) {
            throw new NoSuchElementException("No doctor exists with the given id");
        }
        return doctor
                .get()
                .getAppointments()
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus()
                        .equals(AppointmentStatus.not_completed))
                .map(AppointmentService::dtoMapper)
                .collect(Collectors.toList());
    }



    public static DoctorResponseDTO dtoMapper(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getPhoneNumber(),
                doctor.getMedicalService() == null ? null : doctor.getMedicalService().getName());
    }
}
