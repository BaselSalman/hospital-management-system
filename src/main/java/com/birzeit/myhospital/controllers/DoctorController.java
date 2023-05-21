package com.birzeit.myhospital.controllers;

import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.doctor.DoctorRequestDTO;
import com.birzeit.myhospital.dtos.doctor.DoctorResponseDTO;
import com.birzeit.myhospital.services.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/upcoming_appointments")
    public List<AppointmentResponseDTO> getUpcomingAppointments(@RequestParam long id) {
        return doctorService.getUpcomingAppointments(id);
    }

    @PostMapping
    public void addDoctor(@RequestBody DoctorRequestDTO doctor) {
        doctorService.addDoctor(doctor);
    }

    @DeleteMapping
    public void deleteDoctor(@RequestParam long id) {
        doctorService.deleteDoctorById(id);
    }

}
