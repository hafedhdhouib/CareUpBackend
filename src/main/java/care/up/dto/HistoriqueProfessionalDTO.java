package care.up.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import care.up.model.HistoriqueProfessional;
import lombok.Data;
@Data

public class HistoriqueProfessionalDTO {
    private Long id;

	private String content;

	private LocalDateTime created;

	private ProfessionalDTO professional;

    	private ConsultationRequestDTO consultationRequest;


    public static HistoriqueProfessionalDTO mapToDTO(HistoriqueProfessional entity) {
		if (entity != null) {
			ModelMapper modelMapper = new ModelMapper();
			return mapAllObjectsToDTO(entity, modelMapper.map(entity, HistoriqueProfessionalDTO.class));
		}
		return null;
	}

	private static HistoriqueProfessionalDTO mapAllObjectsToDTO(HistoriqueProfessional entity, HistoriqueProfessionalDTO dto) {
		dto.setConsultationRequest(ConsultationRequestDTO.mapToDTO(entity.getConsultationRequest()));
        dto.setProfessional(ProfessionalDTO.mapToDTO(entity.getProfessional()));
		return dto;
	}

}
