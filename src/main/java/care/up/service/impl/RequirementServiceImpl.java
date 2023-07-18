package care.up.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.model.Requirement;
import care.up.repository.RequirementRepository;
import care.up.service.RequirementService;

@Service
public class RequirementServiceImpl implements RequirementService {

	@Autowired
	RequirementRepository requirementRepository;

	@Override
	public Requirement addRequirement(Requirement requirement) throws SQLIntegrityConstraintViolationException {
		if (requirement != null) {
			try {
				return requirementRepository.save(requirement);
			} catch (Exception e) {
				throw new SQLIntegrityConstraintViolationException();
			}
		}
		return null;
	}

	@Override
	public List<Requirement> getAllRequirements(Pageable pageable) {
		return requirementRepository.findAll(pageable).toList();
	}

	@Override
	public Requirement getRequirementById(Long id) {
		if (id != null) {
			Optional<Requirement> optional = requirementRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	@Override
	public Requirement editRequirement(Requirement requirement) {
		if (requirement != null) {
			Optional<Requirement> optional = requirementRepository.findById(requirement.getId());
			if (optional.isPresent()) {
				Requirement toEdit = optional.get();
				toEdit.setTitle(requirement.getTitle());
				toEdit.setType(requirement.getType());
				return requirementRepository.save(toEdit);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteRequirementById(Long id) {
		if (id != null && requirementRepository.existsById(id)) {
			requirementRepository.deleteById(id);
			return !requirementRepository.existsById(id);
		}
		return false;
	}
}
