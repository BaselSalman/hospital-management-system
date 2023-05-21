package com.birzeit.myhospital.dtos.appointment;

public record AppointmentRequestDTO(int patientId, int doctorId, String date, String time, String reason) {}