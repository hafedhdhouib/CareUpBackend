package care.up.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import care.up.model.ConsultationRequest;
import care.up.model.Professional;

public interface ConsultationRequestService {

	public ConsultationRequest addConsultationRequest(ConsultationRequest consultationRequest);

	public Page<ConsultationRequest> getAllConsultationRequests(Pageable pageable);
	
	public List<ConsultationRequest> getAllByPatientId(Long patientId);
	public List<ConsultationRequest> getAllByprofessionalId(Long professionalId);

	public Number countByProfessionalID(Long professionalId);

	public ConsultationRequest getConsultationRequestById(Long id);

	public ConsultationRequest editConsultationRequest(ConsultationRequest consultationRequest);

	public Boolean deleteConsultationRequestById(Long id);

	public List<Professional> testSendNotif(Long reqId);
	
	public List<Professional> getAllProfessionalOfAllRequestOfPatient(Long patientId);

	public ConsultationRequest acceptConsultationRequest(Long professionalId, ConsultationRequest req);
	
	public Long countConsultationRequest();

}
