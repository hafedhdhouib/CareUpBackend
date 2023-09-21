package care.up.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.dto.PatientDTO;
import care.up.model.Patient;
import care.up.repository.PatientRepository;
import care.up.repository.ProfessionalRepository;
import care.up.service.PatientService;
@Service
@Transactional

public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepository patientRepository;

	public List<PatientDTO> getAllPatient(Pageable pageable) {
return  patientRepository.findAll(pageable).stream().map(PatientDTO::mapToDTO).collect(Collectors.toList());	
}
	
public Number countPatient() {
	return patientRepository.count();
}
}
