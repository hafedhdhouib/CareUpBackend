package care.up.dto;

import org.modelmapper.ModelMapper;

import care.up.model.Address;
import lombok.Data;

@Data
public class AddressDTO {

	private Long id;

	private String country;

	private String state;

	private String delegation;

	private Float longitude;

	private Float latitude;

	public static AddressDTO mapToDTO(Address entity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entity, AddressDTO.class);
	}
}
