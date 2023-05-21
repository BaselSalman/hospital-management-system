package com.birzeit.myhospital.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient-seq")
    @GenericGenerator(name = "patient-seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "patient_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")}
    )
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(name = "gender", updatable = false, nullable = false, columnDefinition = "TEXT")
    private String gender;

    @Column(name = "dob", nullable = false, columnDefinition = "DATE")
    private LocalDate dob;

    @Column(name = "phone_number", nullable = false, unique = true, columnDefinition = "VARCHAR(15)")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(30)")
    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors = new HashSet<>();

}
