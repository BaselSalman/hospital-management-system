package com.birzeit.myhospital.services;

import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.patient.PatientRequestDTO;
import com.birzeit.myhospital.dtos.patient.PatientResponseDTO;
import com.birzeit.myhospital.entities.AppointmentStatus;
import com.birzeit.myhospital.entities.Patient;
import com.birzeit.myhospital.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientService::dtoMapper)
                .collect(Collectors.toList());
    }

    public Optional<Patient> getPatientById(long id) {
        return patientRepository.findById(id);
    }

    public List<AppointmentResponseDTO> getAppointmentHistory(long patientId) {
        Optional<Patient> patient = getPatientById(patientId);
        if(patient.isEmpty()) {
            throw new NoSuchElementException("No patient exists with the given id");
        }
        return patient
                .get()
                .getAppointments()
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus().equals(AppointmentStatus.completed))
                .map(AppointmentService::dtoMapper)
                .collect(Collectors.toList());
    }

    public void addPatient(PatientRequestDTO patientRequest) {
        Patient patient = Patient
                .builder()
                .name(patientRequest.name())
                .gender(patientRequest.gender())
                .dob(LocalDateFormatter.makeDateFromString(patientRequest.dob()))
                .phoneNumber(patientRequest.phoneNumber())
                .email(patientRequest.email())
                .build();
        patientRepository.save(patient);
    }

    public void deletePatientById(long id) {
        try {
            patientRepository.deleteById(id);
        } catch (Exception e) {
            throw new NoSuchElementException("No patient exists with the given id");
        }
    }

    public static PatientResponseDTO dtoMapper(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getGender(),
                LocalDateFormatter.formatDate(patient.getDob()),
                patient.getPhoneNumber(),
                patient.getEmail());
    }
}
