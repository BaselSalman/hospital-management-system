package com.birzeit.myhospital.controllers;

import com.birzeit.myhospital.dtos.appointment.AppointmentRequestDTO;
import com.birzeit.myhospital.dtos.appointment.AppointmentResponseDTO;
import com.birzeit.myhospital.dtos.appointment.DateTimeDTO;
import com.birzeit.myhospital.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAppointments();
    }

    @GetMapping("/details")
    public AppointmentResponseDTO getAppointmentDetails(@RequestParam long id) {
        return appointmentService.getAppointmentDetails(id);
    }

    @PostMapping
    public void addAppointment(@RequestBody AppointmentRequestDTO appointment) {
        appointmentService.addAppointment(appointment);
    }

    @DeleteMapping
    public void deleteAppointment(@RequestParam long id) {
        appointmentService.deleteAppointmentById(id);
    }

    @PatchMapping("/cancel")
    public void cancelAppointment(@RequestParam long id) {
        appointmentService.cancelAppointment(id);
    }

    @PatchMapping("/complete")
    public void completeAppointment(@RequestParam long id) {
        appointmentService.completeAppointment(id);
    }

    @PatchMapping("/reschedule")
    public void rescheduleAppointment(
            @RequestParam long id,
            @RequestBody DateTimeDTO newDateTimeDTO)
            throws IOException {
        appointmentService.rescheduleAppointment(id, newDateTimeDTO);
    }

}
