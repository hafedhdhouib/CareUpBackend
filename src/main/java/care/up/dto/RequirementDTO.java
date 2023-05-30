package care.up.dto;

import org.modelmapper.ModelMapper;

import care.up.enums.RequestType;
import care.up.model.Requirement;
import lombok.Data;

@Data
public class RequirementDTO {

	private Long id;

	private RequestType type;

	private String title;

	public static RequirementDTO mapToDTO(Requirement entity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entity, RequirementDTO.class);
	}
}
