package org.spinescope.diagnosisapi.domain.diagnosis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false, length = 100)
    private LocalDate date;

    @Column(name = "disease", nullable = false, length = 100)
    private String disease;

    @Column(name = "accuracy", nullable = false)
    private Integer accuracy;

    @Column(name = "image")
    private byte[] image;

}