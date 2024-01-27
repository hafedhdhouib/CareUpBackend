package care.up.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import care.up.model.HistoriqueSms;
import care.up.repository.HistoriqueSmsRepository;
import care.up.repository.ProfessionalRepository;
import care.up.service.HistoriqueSmsService;
@Service

public class HistoriqueSmsServiceImpl implements HistoriqueSmsService {
	@Autowired
	HistoriqueSmsRepository historiqueSmsRepository;

	@Autowired
	ProfessionalRepository professionalRepository;

	
	@Override
	public HistoriqueSms createHistoriqueSmsDemande(Long profid){
		HistoriqueSms historiqueSms = new HistoriqueSms();
		historiqueSms.setContent("Demande");
		historiqueSms.setProfessional(professionalRepository.findById(profid).get());
		return historiqueSmsRepository.save(historiqueSms);
		
	}


	@Override
	public HistoriqueSms createHistoriqueSmsRappel(Long profid) {
		HistoriqueSms historiqueSms = new HistoriqueSms();
		historiqueSms.setContent("Rappel");
		historiqueSms.setProfessional(professionalRepository.findById(profid).get());
		return historiqueSmsRepository.save(historiqueSms);
	}


	@Override
	public Long countHistoriqueSmsOfProf(Long profId) {
		// TODO Auto-generated method stub
		return historiqueSmsRepository.countHistoriqueSmsByProf(profId);
	}

}
