package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.CareRecord;

public interface CareRecordService {

	public CareRecord addCareRecord(CareRecord careRecord);

	public List<CareRecord> getPatientCareRecordsWithSpecProfessional(Long professionalId, Long patientId,
			Pageable pageable);

	public Boolean deleteCareRecord(Long id);
	
	
}
