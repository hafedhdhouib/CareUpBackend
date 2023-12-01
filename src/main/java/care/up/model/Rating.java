package care.up.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import care.up.dto.RatingDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UniquePatientAndProfessional", columnNames = { "patient_id", "professional_id" }) })
public class Rating extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 1)
	private int value;

	private String note;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Professional professional;

	public static Rating mapToEntity(RatingDTO dto) {
		if (dto != null) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(dto, Rating.class);
		}
		return null;
	}
}
