package com.birzeit.myhospital.controllers;

import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.patient.PatientRequestDTO;
import com.birzeit.myhospital.dtos.patient.PatientResponseDTO;
import com.birzeit.myhospital.services.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/appointments_history")
    public List<AppointmentResponseDTO> getAppointmentHistory(@RequestParam(name = "id") long patientId) {
        return patientService.getAppointmentHistory(patientId);
    }

    @PostMapping
    public void addPatient(@RequestBody PatientRequestDTO patient) {
        patientService.addPatient(patient);
    }

    @DeleteMapping
    public void deletePatient(@RequestParam long id) {
        patientService.deletePatientById(id);
    }

}
