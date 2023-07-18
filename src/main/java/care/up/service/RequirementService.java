package care.up.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.Requirement;

public interface RequirementService {

	public Requirement addRequirement(Requirement requirement) throws SQLIntegrityConstraintViolationException;

	public List<Requirement> getAllRequirements(Pageable pageable);

	public Requirement getRequirementById(Long id);
	
	public Requirement editRequirement(Requirement requirement);

	public Boolean deleteRequirementById(Long id);
}
