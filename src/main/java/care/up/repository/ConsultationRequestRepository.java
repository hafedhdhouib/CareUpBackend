package care.up.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import care.up.enums.RequestStatus;
import care.up.model.ConsultationRequest;

public interface ConsultationRequestRepository extends JpaRepository<ConsultationRequest, Long> {
	Page<ConsultationRequest> findAllByOrderByIdDesc(Pageable pageable);

	List<ConsultationRequest> findByPatientId(Long patientId);
	
	List<ConsultationRequest> findByProfessionalId(Long professionalId);

	Number countByProfessionalId(Long professionalId);
	
	List<ConsultationRequest> findByPatientIdAndStatus(Long patientId, RequestStatus requestStatus);

	

}
