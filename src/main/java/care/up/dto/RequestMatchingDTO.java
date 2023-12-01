package care.up.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import care.up.model.RequestMatching;
import lombok.Data;

@Data
public class RequestMatchingDTO {

	private Long id;

	private ProfessionalDTO professional;

	private ConsultationRequestDTO consultationRequest;

	private LocalDateTime created;

	public static RequestMatchingDTO mapToDTO(RequestMatching entity) {
		ModelMapper modelMapper = new ModelMapper();
		return mapAllObjectsToDTO(entity, modelMapper.map(entity, RequestMatchingDTO.class));
	}

	private static RequestMatchingDTO mapAllObjectsToDTO(RequestMatching entity, RequestMatchingDTO dto) {
		if (entity.getProfessional() != null) {
			dto.setProfessional(ProfessionalDTO.mapToDTO(entity.getProfessional()));
		}
		if (entity.getConsultationRequest() != null) {
			dto.setConsultationRequest(ConsultationRequestDTO.mapToDTO(entity.getConsultationRequest()));
		}
		return dto;
	}
}
