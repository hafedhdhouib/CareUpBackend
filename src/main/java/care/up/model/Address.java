package care.up.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import care.up.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Address extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 50)
	private String country;

	@NotNull
	@Size(min = 3, max = 50)
	private String state;

	@NotNull
	@Size(min = 3, max = 50)
	private String delegation;

	private Float longitude;

	private Float latitude;

	public static Address mapToEntity(AddressDTO dto) {
		if (dto != null) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(dto, Address.class);
	}
		return null;
	}

	public static void patch(Address from, Address to) {
		if (from != null && to != null) {
			to.setCountry(from.getCountry());
			to.setState(from.getState());
			to.setDelegation(from.getDelegation());
			to.setLatitude(from.getLatitude());
			to.setLongitude(from.getLongitude());
		}
	}
}
