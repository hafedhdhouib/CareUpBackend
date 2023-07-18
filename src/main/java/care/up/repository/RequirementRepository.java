package care.up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
	

}
