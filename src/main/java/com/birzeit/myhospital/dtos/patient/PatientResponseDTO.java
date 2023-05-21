package com.birzeit.myhospital.dtos.patient;

public record PatientResponseDTO(long id, String name, String gender, String dob, String phoneNumber, String email) {}