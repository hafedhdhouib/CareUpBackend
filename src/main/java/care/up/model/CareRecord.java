package care.up.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.modelmapper.ModelMapper;

import care.up.dto.CareRecordDTO;
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
public class CareRecord extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 15)
	private String fc;

	@Column(length = 15)
	private String pa;

	@Column(length = 15)
	private String spO2;

	@Column(length = 15)
	private String fr;

	@Column(length = 15)
	private String t;

	@Column(length = 15)
	private String gly;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private Set<Requirement> performedCares;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private Set<ChronicDisease> chronicDiseases;

	@Column(length = 1000)
	private String notes;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Professional professional;

	public static CareRecord mapToEntity(CareRecordDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, CareRecord.class);
		}
		return null;
	}
}
