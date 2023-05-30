package care.up.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.model.ChronicDisease;
import care.up.repository.ChronicDiseaseRepository;
import care.up.service.ChronicDiseaseService;

@Service
public class ChronicDiseaseServiceImpl implements ChronicDiseaseService {

	@Autowired
	ChronicDiseaseRepository chronicDiseaseRepository;

	@Override
	public ChronicDisease addChronicDisease(ChronicDisease chronicDisease)
			throws SQLIntegrityConstraintViolationException {
		if (chronicDisease != null) {
			try {
				return chronicDiseaseRepository.save(chronicDisease);
			} catch (Exception e) {
				throw new SQLIntegrityConstraintViolationException();
			}
		}
		return null;
	}

	@Override
	public List<ChronicDisease> getAllChronicDiseases(Pageable pageable) {
		return chronicDiseaseRepository.findAll(pageable).toList();
	}

	@Override
	public ChronicDisease getChronicDiseaseById(Long id) {
		if (id != null) {
			Optional<ChronicDisease> optional = chronicDiseaseRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	@Override
	public ChronicDisease editChronicDisease(ChronicDisease chronicDisease) {
		if (chronicDisease != null) {
			Optional<ChronicDisease> optional = chronicDiseaseRepository.findById(chronicDisease.getId());
			if (optional.isPresent()) {
				ChronicDisease toEdit = optional.get();
				toEdit.setTitle(chronicDisease.getTitle());
				return chronicDiseaseRepository.save(toEdit);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteChronicDiseaseById(Long id) {
		if (id != null && chronicDiseaseRepository.existsById(id)) {
			chronicDiseaseRepository.deleteById(id);
			return !chronicDiseaseRepository.existsById(id);
		}
		return false;
	}

}
