package care.up.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import care.up.model.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientDTO extends UserDTO {

	public static PatientDTO mapToDTO(Patient entity) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Patient, PatientDTO>() {
			@Override
			protected void configure() {
				skip().setPassword(null);
			}
		});
		return modelMapper.map(entity, PatientDTO.class);
	}
}
