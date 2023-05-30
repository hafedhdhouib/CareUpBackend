package care.up.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.enums.RequestStatus;
import care.up.model.ConsultationRequest;

public interface ConsultationRequestRepository extends JpaRepository<ConsultationRequest, Long> {

	
	List<ConsultationRequest> findByPatientId(Long patientId);
	
	List<ConsultationRequest> findByPatientIdAndStatus(Long patientId, RequestStatus requestStatus);


}
