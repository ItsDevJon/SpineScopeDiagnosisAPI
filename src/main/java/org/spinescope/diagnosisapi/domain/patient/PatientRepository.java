package org.spinescope.diagnosisapi.domain.patient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Integer> {
    Optional<PatientEntity> findByNameAndSurname(String name, String surname);
}
