package care.up.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.Requirement;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
	
	public Optional<Requirement> findByTitle(String title);

}
