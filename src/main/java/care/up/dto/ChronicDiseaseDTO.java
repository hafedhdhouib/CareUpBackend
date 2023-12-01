package care.up.dto;

import org.modelmapper.ModelMapper;

import care.up.model.ChronicDisease;
import lombok.Data;

@Data
public class ChronicDiseaseDTO {

	private Long id;

	private String title;

	public static ChronicDiseaseDTO mapToDTO(ChronicDisease entity) {
		if (entity != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(entity, ChronicDiseaseDTO.class);
		}
		return null;
	}
}
