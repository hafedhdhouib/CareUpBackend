package care.up.model;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import care.up.dto.RequirementDTO;
import care.up.enums.RequestType;
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
public class Requirement extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RequestType type;

	@NotNull
	private String title;

	public static Requirement mapToEntity(RequirementDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, Requirement.class);
		}
		return null;
	}

}
