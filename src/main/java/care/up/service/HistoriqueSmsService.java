package care.up.service;

import care.up.model.HistoriqueSms;

public interface HistoriqueSmsService {
public HistoriqueSms createHistoriqueSmsDemande(Long profId);
public HistoriqueSms createHistoriqueSmsRappel(Long profId);
public Long countHistoriqueSmsOfProf(Long profId);

}
