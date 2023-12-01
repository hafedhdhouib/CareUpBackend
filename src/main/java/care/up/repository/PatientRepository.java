package care.up.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;

import care.up.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
Page<Patient> findAllByOrderByIdDesc(Pageable pageable);

public List<Patient> findByAddressStateOrderByIdDesc(
		@NotNull String state);	
public List<Patient> findByAddressDelegationOrderByIdDesc(
		@NotNull String delegation);	
public Long countByAddressState(
		@NotNull String state);
		
public Long countByAddressDelegation(
		@NotNull String delegation
		);	

}
