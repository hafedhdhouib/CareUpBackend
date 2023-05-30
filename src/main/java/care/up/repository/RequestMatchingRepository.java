package care.up.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import care.up.model.RequestMatching;

public interface RequestMatchingRepository extends JpaRepository<RequestMatching, Long> {

	public Page<RequestMatching> findByProfessionalId(Long professionalId, Pageable pageable);

	@Modifying
	@Query ("DELETE FROM RequestMatching WHERE consultation_request_id = :consultation_request_id and professional_id <> :professional_id")
	public void delete(@Param("consultation_request_id") long consultation_request_id,@Param("professional_id") long professional_id);

}
