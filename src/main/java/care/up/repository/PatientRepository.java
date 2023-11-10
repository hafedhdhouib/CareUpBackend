package care.up.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import care.up.enums.ProfessionType;

import org.springframework.data.domain.Page;

import care.up.model.Patient;
import care.up.model.Professional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
