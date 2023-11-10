package care.up.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.model.Abonnement;
import care.up.repository.AbonnementRepository;
import care.up.service.AbonnementService;

@Service

public class AbonnementServiceImpl implements AbonnementService {

	@Autowired
	AbonnementRepository abonnementRepository;
	
	@Override
	public Abonnement addAbonnement(Abonnement abonnement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Abonnement> getAllAbonnement(Pageable pageable) {
		// TODO Auto-generated method stub
		return abonnementRepository.findAll(pageable).toList();
	}

}
