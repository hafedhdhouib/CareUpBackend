package care.up.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import care.up.enums.ProfessionType;
import care.up.model.Professional;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfessionalDTO extends UserDTO {

	private ProfessionType profession;

	private String diploma;

	private int tourArea; // in km


	public static ProfessionalDTO mapToDTO(Professional entity) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Professional, ProfessionalDTO>() {
			@Override
			protected void configure() {
				skip(destination.getPassword());
			}
		});
		return mapAllObjectsToDTO(entity, modelMapper.map(entity, ProfessionalDTO.class));
	}

	private static ProfessionalDTO mapAllObjectsToDTO(Professional entity, ProfessionalDTO dto) {

		return dto;
	}
}
