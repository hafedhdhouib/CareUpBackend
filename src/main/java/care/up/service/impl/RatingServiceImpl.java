package care.up.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.Rating;
import care.up.model.User;
import care.up.repository.RatingRepository;
import care.up.repository.UserRepository;
import care.up.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Rating addRating(Rating rating) {
		if (rating != null) {
			Optional<User> optional = userRepository.findById(rating.getPatient().getId());
			Optional<User> optional2 = userRepository.findById(rating.getProfessional().getId());
			if (optional.isPresent() && optional2.isPresent()) {
				rating.setPatient((Patient) optional.get());
				rating.setProfessional((Professional) optional2.get());
				return ratingRepository.save(rating);
			}

		}
		return null;
	}

	@Override
	public List<Rating> getAllRatings(Pageable pageable) {
		return ratingRepository.findAll(pageable).toList();
	}

	@Override
	public Rating getRatingById(Long id) {
		if (id != null) {
			Optional<Rating> optional = ratingRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	@Override
	public Rating editRating(Rating rating) {
		if (rating != null) {
			Optional<Rating> optional = ratingRepository.findById(rating.getId());
			if (optional.isPresent()) {
				Rating toEdit = optional.get();
				toEdit.setValue(rating.getValue());
				toEdit.setNote(rating.getNote());
				return ratingRepository.save(toEdit);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteRatingById(Long id) {
		if (id != null && ratingRepository.existsById(id)) {
			ratingRepository.deleteById(id);
			return !ratingRepository.existsById(id);
		}
		return false;
	}

	@Override
	public Rating findByPatientIdAndProfessionalId(Long patientId, Long professionalId) {
		if (patientId != null && professionalId != null) {
			Optional<Rating> optional = ratingRepository.findByPatientIdAndProfessionalId(patientId, professionalId);
			if (optional.isPresent()) {
				return optional.get();
			}

		}
		return null;
	}

	@Override
	public Double getProfessionalRating(Long professionalId) {
		return ratingRepository.getProfessionalRating(professionalId);
	}

}
