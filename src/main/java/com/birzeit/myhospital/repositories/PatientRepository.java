package com.birzeit.myhospital.repositories;

import com.birzeit.myhospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
