package com.birzeit.myhospital.controllers;

import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.doctor.DoctorResponseDTO;
import com.birzeit.myhospital.dtos.medical_service.MedicalServiceRequestDTO;
import com.birzeit.myhospital.dtos.medical_service.MedicalServiceResponseDTO;
import com.birzeit.myhospital.services.MedicalServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical_services")
public class MedicalServiceController {

    private final MedicalServiceService medicalServiceService;

    MedicalServiceController(MedicalServiceService medicalServiceService) {
        this.medicalServiceService = medicalServiceService;
    }

    @GetMapping
    public List<MedicalServiceResponseDTO> getAllMedicalServices() {
        return medicalServiceService.getMedicalServices();
    }

    @GetMapping("/doctors")
    public List<DoctorResponseDTO> getDoctorsInMedicalService(@RequestParam int id) {
        return medicalServiceService.getDoctors(id);
    }

    @GetMapping("/appointments")
    public List<AppointmentResponseDTO> getAppointmentsOfMedicalService(@RequestParam int id) {
        return medicalServiceService.getAppointments(id);
    }

    @PostMapping
    public void addMedicalService(@RequestBody MedicalServiceRequestDTO medicalService) {
        medicalServiceService.addMedicalService(medicalService);
    }

    @DeleteMapping
    public void deleteMedicalService(@RequestParam int id) {
        medicalServiceService.deleteMedicalServiceById(id);
    }

}
