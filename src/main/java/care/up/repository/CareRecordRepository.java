package care.up.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.CareRecord;

public interface CareRecordRepository extends JpaRepository<CareRecord, Long> {

	public List<CareRecord> findByProfessionalIdAndPatientId(Long professionalId, Long patientId, Pageable pageable);

}
