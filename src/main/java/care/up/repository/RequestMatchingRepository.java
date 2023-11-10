package care.up.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import care.up.dto.RequestMatchingDTO;
import care.up.model.ConsultationRequest;
import care.up.model.Professional;
import care.up.model.RequestMatching;

public interface RequestMatchingRepository extends JpaRepository<RequestMatching, Long> {

	public Page<RequestMatching> findByProfessionalId(Long professionalId, Pageable pageable);
	
	public Page<RequestMatching> findByConsultationRequestId(Long onsultationRequestId, Pageable pageable);

	public Optional<RequestMatching>  findByConsultationRequestIdAndProfessionalId(long consultationRequestId,long professionalId);

	
	@Modifying
	@Query ("DELETE FROM RequestMatching WHERE consultation_request_id = :consultation_request_id and professional_id <> :professional_id")
	public void delete(@Param("consultation_request_id") long consultation_request_id,@Param("professional_id") long professional_id);
	
	}
