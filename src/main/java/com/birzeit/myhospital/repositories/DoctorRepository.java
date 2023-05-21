package com.birzeit.myhospital.repositories;

import com.birzeit.myhospital.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
