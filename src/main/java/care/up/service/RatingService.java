package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.Rating;

public interface RatingService {

	public Rating addRating(Rating rating);

	public List<Rating> getAllRatings(Pageable pageable);

	public Rating getRatingById(Long id);

	public Rating editRating(Rating rating);

	public Boolean deleteRatingById(Long id);
	
	public Rating findByPatientIdAndProfessionalId(Long patientId, Long professionalId);
	
	public Double getProfessionalRating(Long professionalId);
}
