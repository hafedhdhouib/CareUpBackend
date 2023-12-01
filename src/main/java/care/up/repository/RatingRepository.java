package care.up.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import care.up.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	public Optional<Rating> findByPatientIdAndProfessionalId(Long patientId, Long ProfessionalId);
	
	@Query("SELECT AVG(r.value) FROM Rating r WHERE r.professional.id = ?1") 
	public Double getProfessionalRating(Long professionalId);

}
