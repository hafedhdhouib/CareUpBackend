package care.up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
