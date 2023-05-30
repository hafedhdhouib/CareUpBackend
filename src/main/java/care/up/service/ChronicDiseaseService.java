package care.up.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.ChronicDisease;

public interface ChronicDiseaseService {

	public ChronicDisease addChronicDisease(ChronicDisease chronicDisease)
			throws SQLIntegrityConstraintViolationException;

	public List<ChronicDisease> getAllChronicDiseases(Pageable pageable);

	public ChronicDisease getChronicDiseaseById(Long id);

	public ChronicDisease editChronicDisease(ChronicDisease chronicDisease);

	public Boolean deleteChronicDiseaseById(Long id);
}
