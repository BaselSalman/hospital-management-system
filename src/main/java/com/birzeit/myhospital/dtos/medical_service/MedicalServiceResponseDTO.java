package com.birzeit.myhospital.dtos.medical_service;

public record MedicalServiceResponseDTO(int id, String name, String description, String estimatedDuration, double cost) {}