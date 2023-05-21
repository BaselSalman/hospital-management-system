package com.birzeit.myhospital.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor-seq")
    @GenericGenerator(name = "doctor-seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "doctor_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")}
    )
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true, columnDefinition = "VARCHAR(15)")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(30)")
    private String email;

    @ManyToMany
    @JoinTable(name = "doctors_treat_patients",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private Set<Patient> patients = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "medical_service_id")
    private MedicalService medicalService;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

}
