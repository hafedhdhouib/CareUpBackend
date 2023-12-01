package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.RatingDTO;
import care.up.model.Rating;
import care.up.service.RatingService;

@RequestMapping("/rating")
@RestController
@CrossOrigin
public class RatingController {
	@Autowired
	RatingService ratingService;

	@PostMapping("create")
	public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO dto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(RatingDTO.mapToDTO(ratingService.addRating(Rating.mapToEntity(dto))));
	}

	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<RatingDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<RatingDTO> dtos = ratingService.getAllRatings(PageRequest.of(page, size)).stream().map(RatingDTO::mapToDTO)
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<RatingDTO> getRatingById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(RatingDTO.mapToDTO(ratingService.getRatingById(id)));
	}

	@PutMapping("edit-rating")
	public ResponseEntity<RatingDTO> editRating(@RequestBody RatingDTO dto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(RatingDTO.mapToDTO(ratingService.editRating(Rating.mapToEntity(dto))));
	}

	@DeleteMapping("delete-by-id/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.deleteRatingById(id));
	}
	
	@GetMapping("get-rating-Between/{patientId}/{professionalId}") 
		ResponseEntity<RatingDTO> getRatingBetween(@PathVariable(name = "patientId") Long patientId,
				@PathVariable(name = "professionalId") Long professionalId) {
		Rating rating = ratingService.findByPatientIdAndProfessionalId(patientId, professionalId);
		if (rating != null) {
			return ResponseEntity.status(HttpStatus.OK).body(RatingDTO.mapToDTO(rating));
		}
			return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("get-professional-rating-by-id/{professionalId}")
	public ResponseEntity<Double> getProfessionalRating(@PathVariable(name = "professionalId") Long professionalId) {
		Double res = ratingService.getProfessionalRating(professionalId);
		if (res != null) {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0.0);
		}
	}

}
