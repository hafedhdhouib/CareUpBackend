package care.up.dto;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Id;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import care.up.enums.ProfessionType;
import care.up.enums.RequestStatus;
import care.up.model.ConsultationRequest;
import lombok.Data;

@Data
public class ConsultationRequestDTO {

	@Id
	private Long id;

	private String patientFullName;

	private int patientAge;

	private String patientGender;

	private ProfessionType type;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	private String hourlyAvailability;

	private String staffGenre;

	private RequestStatus status;

	private Set<RequirementDTO> requirements;

	private Set<ChronicDiseaseDTO> chronicDiseases;

	private ProfessionalDTO professional;

	private PatientDTO patient;

	public static ConsultationRequestDTO mapToDTO(ConsultationRequest entity) {
		ModelMapper modelMapper = new ModelMapper();
		return mapAllObjectsToDTO(entity, modelMapper.map(entity, ConsultationRequestDTO.class));
	}

	private static ConsultationRequestDTO mapAllObjectsToDTO(ConsultationRequest entity, ConsultationRequestDTO dto) {
		if (entity.getRequirements() != null) {
			dto.setRequirements(
					entity.getRequirements().stream().map(r -> RequirementDTO.mapToDTO(r)).collect(Collectors.toSet()));
		}
		if (entity.getChronicDiseases() != null) {
			dto.setChronicDiseases(entity.getChronicDiseases().stream().map(c -> ChronicDiseaseDTO.mapToDTO(c))
					.collect(Collectors.toSet()));
		}
		if (entity.getProfessional() != null) {
			dto.setProfessional(ProfessionalDTO.mapToDTO(entity.getProfessional()));
		}
		dto.setPatient(PatientDTO.mapToDTO(entity.getPatient()));
		return dto;
	}
}
