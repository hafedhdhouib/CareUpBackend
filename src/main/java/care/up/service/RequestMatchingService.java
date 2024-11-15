package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.ConsultationRequest;
import care.up.model.Professional;
import care.up.model.RequestMatching;

public interface RequestMatchingService {

	public List<Professional> MatchingRequestWithProfessional(ConsultationRequest req);

	public List<RequestMatching> getAllRequestsMatching(Pageable pageable);
	
	public List<RequestMatching> getAllRequestByProfessionalId(Long professionalId, Pageable pageable);
	
	public List<RequestMatching> getAllRequestByConsultationRequestId(Long consultationRequestId, Pageable pageable);

	public RequestMatching getRequestById(Long requestId);
	
	public boolean refuseRequestByProfessional(Long requestId);

	public void delete(long consultationRequestId, long professionalId);
	
	public void deleteByproId(long consultationRequestId, long professionalId);
	public  long MatchingRequestWithProfessionalSpe(long reqID,long professionalId);




	
}
