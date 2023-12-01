package care.up.dto;

import org.modelmapper.ModelMapper;

import care.up.model.Rating;
import lombok.Data;

@Data
public class RatingDTO {

	private Long id;

	private int value;
	
	private String note;

	private PatientDTO patient;

	private ProfessionalDTO professional;

	public static RatingDTO mapToDTO(Rating entity) {
		ModelMapper modelMapper = new ModelMapper();
		return mapAllObjectsToDTO(entity, modelMapper.map(entity, RatingDTO.class));
	}

	private static RatingDTO mapAllObjectsToDTO(Rating entity, RatingDTO dto) {
		dto.setPatient(PatientDTO.mapToDTO(entity.getPatient()));
		dto.setProfessional(ProfessionalDTO.mapToDTO(entity.getProfessional()));
		return dto;
	}
}
