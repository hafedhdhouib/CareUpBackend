package care.up.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.model.CareRecord;
import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.Requirement;
import care.up.model.ChronicDisease;

import care.up.model.User;
import care.up.repository.CareRecordRepository;
import care.up.repository.RequirementRepository;
import care.up.repository.ChronicDiseaseRepository;

import care.up.repository.UserRepository;
import care.up.service.CareRecordService;

@Service
public class CareRecordServiceImpl implements CareRecordService {

	@Autowired
	CareRecordRepository careRecordRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RequirementRepository requirementRepository;
	
	@Autowired 
	ChronicDiseaseRepository chronicDiseaseRepository;

	@Override
	public CareRecord addCareRecord(CareRecord careRecord) {
		if (careRecord != null) {
			Optional<User> patient = userRepository.findById(careRecord.getPatient().getId());
			Optional<User> professional = userRepository.findById(careRecord.getProfessional().getId());
			if (patient.isPresent() && professional.isPresent()) {
				careRecord.setPatient((Patient) patient.get());
				careRecord.setProfessional((Professional) professional.get());
			}
			/////////// 5idemti
			Set<ChronicDisease> chronicDiseases = new HashSet<>();
			careRecord.getChronicDiseases().forEach(c -> {
				Optional<ChronicDisease> optional = chronicDiseaseRepository.findById(c.getId());
				if (optional.isPresent()) {
					chronicDiseases.add(optional.get());
				}
			});
			careRecord.setChronicDiseases(chronicDiseases);

			////////////////
			Set<Requirement> performedCares = new HashSet<>();
			careRecord.getPerformedCares().forEach(r -> {
				Optional<Requirement> optional = requirementRepository.findById(r.getId());
				if (optional.isPresent()) {
					performedCares.add(optional.get());
				}
			});
			careRecord.setPerformedCares(performedCares);
			return careRecordRepository.save(careRecord);
		}
		return null;
	}

	@Override
	public List<CareRecord> getPatientCareRecordsWithSpecProfessional(Long professionalId, Long patientId,
			Pageable pageable) {
		return careRecordRepository.findByProfessionalIdAndPatientId(professionalId, patientId, pageable);
	}

	@Override
	public Boolean deleteCareRecord(Long id) {
		if (id != null && careRecordRepository.existsById(id)) {
			careRecordRepository.deleteById(id);
			return !careRecordRepository.existsById(id);
		}
		return false;
	}

}
