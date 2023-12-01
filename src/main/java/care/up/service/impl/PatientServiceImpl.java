package care.up.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	public Page<Patient> getAllPatient(Pageable pageable) {
return  patientRepository.findAllByOrderByIdDesc(pageable);	
}
	
public Number countPatient() {
	return patientRepository.count();
}

@Override
public List<Patient> findByAddressStateOrderByIdDesc(@NotNull String state) {
	return patientRepository.findByAddressStateOrderByIdDesc(state);
}

@Override
public List<Patient> findByAddressDelegationOrderByIdDesc(@NotNull String delegation) {
	return patientRepository.findByAddressDelegationOrderByIdDesc(delegation);
}

@Override
public Number countByAddressState(@NotNull String state) {
		return patientRepository.countByAddressState(state);
}

@Override
public Number countByAddressDelegation(@NotNull String delegation) {
	return patientRepository.countByAddressDelegation(delegation);
}





}
