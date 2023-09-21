package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.dto.PatientDTO;
import care.up.model.Patient;
import care.up.repository.PatientRepository;

public interface PatientService {
	public List<PatientDTO> getAllPatient(Pageable pageable);
	public Number countPatient();

}
