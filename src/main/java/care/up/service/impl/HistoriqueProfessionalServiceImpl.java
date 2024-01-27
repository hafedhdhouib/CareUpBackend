package care.up.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import care.up.model.HistoriqueProfessional;
import care.up.model.RequestMatching;
import care.up.repository.HistoriqueProfessionalRepository;
import care.up.repository.HistoriqueSmsRepository;
import care.up.repository.ProfessionalRepository;
import care.up.repository.RequestMatchingRepository;
import care.up.service.HistoriqueProfessionalService;
import care.up.service.RequestMatchingService;
@Service
public class HistoriqueProfessionalServiceImpl implements HistoriqueProfessionalService {
	@Autowired
	HistoriqueProfessionalRepository historiqueProfessionalRepository;

	@Autowired
	ProfessionalRepository professionalRepository;

	@Autowired
	RequestMatchingRepository requestMatchingRepository;
	
	@Override
	public HistoriqueProfessional createHistoriqueProfessionalAccepter(Long requestId) {
		RequestMatching res=requestMatchingRepository.getRequestById(requestId);
		HistoriqueProfessional historiqueProfessional=new HistoriqueProfessional();
		historiqueProfessional.setConsultationRequest(res.getConsultationRequest());
		historiqueProfessional.setProfessional(res.getProfessional());
		historiqueProfessional.setContent("Refuser");		
		return historiqueProfessionalRepository.save(historiqueProfessional);
	}

	@Override
	public HistoriqueProfessional createHistoriqueProfessionalRefuser(Long requestId) {
		RequestMatching res=requestMatchingRepository.getRequestById(requestId);
		HistoriqueProfessional historiqueProfessional=new HistoriqueProfessional();
		historiqueProfessional.setConsultationRequest(res.getConsultationRequest());
		historiqueProfessional.setProfessional(res.getProfessional());
		historiqueProfessional.setContent("Refuser");		
		return historiqueProfessionalRepository.save(historiqueProfessional);
	}

	@Override
	public Long countHistoriqueSmsOfProf(Long profId) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
