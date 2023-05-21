package com.birzeit.myhospital.dtos.appointment;

import com.birzeit.myhospital.dtos.doctor.DoctorResponseDTO;
import com.birzeit.myhospital.dtos.patient.PatientResponseDTO;
import com.birzeit.myhospital.entities.AppointmentStatus;

public record AppointmentResponseDTO(long appointmentId, PatientResponseDTO patient, DoctorResponseDTO doctor, String date, String time, String reason, AppointmentStatus appointmentStatus) {}