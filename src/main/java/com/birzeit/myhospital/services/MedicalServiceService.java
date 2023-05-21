package com.birzeit.myhospital.services;

import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.doctor.DoctorResponseDTO;
import com.birzeit.myhospital.dtos.medical_service.MedicalServiceRequestDTO;
import com.birzeit.myhospital.dtos.medical_service.MedicalServiceResponseDTO;
import com.birzeit.myhospital.entities.MedicalService;
import com.birzeit.myhospital.repositories.MedicalServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    MedicalServiceService(MedicalServiceRepository medicalServiceRepository) {
        this.medicalServiceRepository = medicalServiceRepository;
    }

    public List<MedicalServiceResponseDTO> getMedicalServices() {
        return medicalServiceRepository.findAll()
                .stream()
                .map(medicalService -> new MedicalServiceResponseDTO(
                        medicalService.getId(),
                        medicalService.getName(),
                        medicalService.getDescription(),
                        LocalDateFormatter.formatTime(medicalService.getEstimatedDuration()),
                        medicalService.getCost()))
                .collect(Collectors.toList());
    }

    public Optional<MedicalService> getMedicalServiceById(int id) {
        return medicalServiceRepository.findById(id);
    }

    public Optional<MedicalService> getMedicalServiceByName(String medicalServiceName) {
        return medicalServiceRepository.findByName(medicalServiceName);
    }

    public List<DoctorResponseDTO> getDoctors(int id) {
        Optional<MedicalService> medicalService = getMedicalServiceById(id);
        if(medicalService.isEmpty()) {
            throw new NoSuchElementException("No medical service exists with the given id");
        }
        return medicalService
                .get()
                .getDoctors()
                .stream()
                .map(DoctorService::dtoMapper)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getAppointments(int id) {
        Optional<MedicalService> medicalService = getMedicalServiceById(id);
        if(medicalService.isEmpty()) {
            throw new NoSuchElementException("No medical service exists with the given id");
        }
        List<AppointmentResponseDTO> appointments = new ArrayList<>();
        medicalService
                .get()
                .getDoctors()
                .forEach(doctor -> doctor
                        .getAppointments()
                        .forEach(appointment -> appointments.add(AppointmentService.dtoMapper(appointment))));
        return appointments;
    }

    public void addMedicalService(MedicalServiceRequestDTO medicalServiceRequest) {
        MedicalService medicalService = MedicalService
                .builder()
                .name(medicalServiceRequest.name())
                .description(medicalServiceRequest.description())
                .estimatedDuration(LocalDateFormatter.makeTimeFromString(medicalServiceRequest.estimatedDuration()))
                .cost(medicalServiceRequest.cost())
                .build();
        medicalServiceRepository.save(medicalService);
    }

    public void deleteMedicalServiceById(int id) {
        try {
            medicalServiceRepository.deleteById(id);
        } catch (Exception e) {
            throw new NoSuchElementException("No medical service exists with the given id");
        }
    }
}
