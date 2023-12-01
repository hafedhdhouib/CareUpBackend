package care.up.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import care.up.dto.ConsultationRequestDTO;
import care.up.enums.ProfessionType;
import care.up.enums.RequestStatus;
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
public class ConsultationRequest extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 50)
	private String patientFullName;

	@NotNull
	private int patientAge;

	@NotNull
	@Size(min = 3, max = 10)
	private String patientGender;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ProfessionType type;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;

	@NotNull
	@Size(max = 20)
	private String hourlyAvailability;

	@NotNull
	@Size(max = 20)
	private String staffGenre;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private Set<Requirement> requirements;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private Set<ChronicDisease> chronicDiseases;

	@ManyToOne
	private Professional professional;

	@ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	private Patient patient;

	public static ConsultationRequest mapToEntity(ConsultationRequestDTO dto) {
		if (dto != null) {
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(dto, ConsultationRequest.class);
		}
		return null;
	}

	public static void patch(ConsultationRequest from, ConsultationRequest to) {
		if (from != null && to != null) {
			to.setPatientFullName(from.getPatientFullName());
			to.setPatientAge(from.getPatientAge());
			to.setPatientGender(from.getPatientGender());
			to.setType(from.getType());
			to.setDate(from.getDate());
			to.setHourlyAvailability(from.getHourlyAvailability());
			to.setStaffGenre(from.getStaffGenre());
			to.setStatus(from.getStatus());
			to.setRequirements(from.getRequirements());
			to.setChronicDiseases(from.getChronicDiseases());
		}
	}
}
