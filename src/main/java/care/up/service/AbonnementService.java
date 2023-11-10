package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.Abonnement;

public interface AbonnementService {
	
	public Abonnement addAbonnement(Abonnement abonnement);
	
	public List<Abonnement> getAllAbonnement(Pageable pageable);

}
