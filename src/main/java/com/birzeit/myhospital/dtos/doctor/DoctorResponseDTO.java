package com.birzeit.myhospital.dtos.doctor;

import lombok.*;

@Builder
public record DoctorResponseDTO(long id, String name, String email, String phoneNumber, String medicalService) {}