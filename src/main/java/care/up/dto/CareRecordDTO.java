package care.up.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import care.up.model.CareRecord;
import lombok.Data;

@Data
public class CareRecordDTO {

	private Long id;

	private String fc;

	private String pa;

	private String spO2;

	private String fr;

	private String t;

	private String gly;

	private Set<RequirementDTO> performedCares;
	
	private Set<ChronicDiseaseDTO> chronicDiseases;

	private String notes;
	
	private LocalDateTime created;

	private PatientDTO patient;

	private ProfessionalDTO professional;

	public static CareRecordDTO mapToDTO(CareRecord CareRecord_Entity) {
		ModelMapper modelMapper = new ModelMapper();
		return mapAllObjectsToDTO(CareRecord_Entity, modelMapper.map(CareRecord_Entity, CareRecordDTO.class));
	}

	private static CareRecordDTO mapAllObjectsToDTO(CareRecord CareRecord_Entity, CareRecordDTO carerecorddto) {
		if (CareRecord_Entity.getPerformedCares() != null) {
			carerecorddto.setPerformedCares(CareRecord_Entity.getPerformedCares().stream().map(r -> RequirementDTO.mapToDTO(r))
					.collect(Collectors.toSet()));
		}
		if (CareRecord_Entity.getChronicDiseases() != null) {
			carerecorddto.setChronicDiseases(CareRecord_Entity.getChronicDiseases().stream().map(c -> ChronicDiseaseDTO.mapToDTO(c))
					.collect(Collectors.toSet()));
		}
		carerecorddto.setProfessional(ProfessionalDTO.mapToDTO(CareRecord_Entity.getProfessional()));
		carerecorddto.setPatient(PatientDTO.mapToDTO(CareRecord_Entity.getPatient()));
		return carerecorddto;
	}
}
