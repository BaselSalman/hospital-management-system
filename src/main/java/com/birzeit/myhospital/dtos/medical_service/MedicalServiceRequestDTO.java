package com.birzeit.myhospital.dtos.medical_service;

public record MedicalServiceRequestDTO(String name, String description, String estimatedDuration, double cost) {}