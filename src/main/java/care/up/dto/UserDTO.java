package care.up.dto;

import java.util.Date;

import javax.validation.constraints.Email;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.format.annotation.DateTimeFormat;

import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.User;
import lombok.Data;

@Data
public class UserDTO implements Cloneable {

	private Long id;

	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	private String phoneNumber;

	private String username;

	@Email
	private String email;

	private String password;

	private String genre;

	private Boolean verified;

	private AddressDTO address;

	public static UserDTO mapToDTO(User entity) {
		ModelMapper modelMapper = new ModelMapper();
		if (entity instanceof Professional) {
			modelMapper.addMappings(new PropertyMap<Professional, ProfessionalDTO>() {
				@Override
				protected void configure() {
					skip(destination.getPassword());
				}
			});
			return mapAllObjectsToDTO(entity, modelMapper.map(entity, ProfessionalDTO.class));
		} else if (entity instanceof Patient) {
			modelMapper.addMappings(new PropertyMap<Patient, PatientDTO>() {
				@Override
				protected void configure() {
					skip().setPassword(null);
				}
			});
			return mapAllObjectsToDTO(entity, modelMapper.map(entity, PatientDTO.class));
		}
		return null;
	}

	private static UserDTO mapAllObjectsToDTO(User entity, UserDTO dto) {
		dto.setAddress(AddressDTO.mapToDTO(entity.getAddress()));
		return dto;
	}

	@Override
	public UserDTO clone() throws CloneNotSupportedException {
		return (UserDTO) super.clone();
	}
}
