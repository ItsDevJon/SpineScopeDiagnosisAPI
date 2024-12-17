package org.spinescope.diagnosisapi.domain.patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import org.spinescope.diagnosisapi.domain.diagnosis.Diagnosis;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "dni", nullable = false, length = 100)
    private String dni;

    @OneToMany
    @JoinColumn(name = "patient_id")
    private Set<Diagnosis> diagnoses = new LinkedHashSet<>();

}