package care.up.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import care.up.dto.PatientDTO;
import care.up.model.Patient;

public interface PatientService {
	Page<Patient> getAllPatient(Pageable pageable);
	
	public Number countPatient();
	
	public List<Patient> findByAddressStateOrderByIdDesc(
			@NotNull String state);	
	public List<Patient> findByAddressDelegationOrderByIdDesc(
			@NotNull String delegation);	
	public Number countByAddressState(
			@NotNull String state
			);	
	public Number countByAddressDelegation(
			@NotNull String delegation
			);	
	

}
