package care.up.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import care.up.model.HistoriqueSms;
import lombok.Data;
@Data
public class HistoriqueSmsDTO {
	private Long id;

	private String content;

	private LocalDateTime created;

	private ProfessionalDTO professional;
	
	public static HistoriqueSmsDTO mapToDTO(HistoriqueSms entity) {
		if (entity != null) {
			ModelMapper modelMapper = new ModelMapper();
			return mapAllObjectsToDTO(entity, modelMapper.map(entity, HistoriqueSmsDTO.class));
		}
		return null;
	}

	private static HistoriqueSmsDTO mapAllObjectsToDTO(HistoriqueSms entity, HistoriqueSmsDTO dto) {
		dto.setProfessional(ProfessionalDTO.mapToDTO(entity.getProfessional()));
		return dto;
	}
}
