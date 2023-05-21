package com.birzeit.myhospital.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Builder
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical-service-seq")
    @GenericGenerator(name = "medial-service-seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "medical_service_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")}
    )
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "name", nullable = false, updatable = false, unique = true, columnDefinition = "VARCHAR(20)")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_duration", nullable = false, columnDefinition = "TIME")
    private LocalTime estimatedDuration;

    @Column(name = "cost", nullable = false)
    private double cost;

    @OneToMany(mappedBy = "medicalService")
    private List<Doctor> doctors = new ArrayList<>();

}
