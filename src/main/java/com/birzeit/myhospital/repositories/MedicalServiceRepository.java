package com.birzeit.myhospital.repositories;

import com.birzeit.myhospital.entities.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Integer> {
    Optional<MedicalService> findByName(String medicalServiceName);
}
