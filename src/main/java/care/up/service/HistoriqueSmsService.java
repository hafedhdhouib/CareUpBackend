package care.up.service;

import java.util.List;

import care.up.model.ConsultationRequest;
import care.up.model.HistoriqueSms;

public interface HistoriqueSmsService {
public HistoriqueSms createHistoriqueSmsDemande(Long profId);
public HistoriqueSms createHistoriqueSmsRappel(Long profId);
public Long countHistoriqueSmsOfProf(Long profId);

}
