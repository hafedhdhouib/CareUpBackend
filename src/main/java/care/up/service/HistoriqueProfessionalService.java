package care.up.service;

import care.up.model.HistoriqueProfessional;

public interface HistoriqueProfessionalService {
	public HistoriqueProfessional createHistoriqueProfessionalAccepter(Long profId);
	public HistoriqueProfessional createHistoriqueProfessionalRefuser(Long profId);

	public Long countHistoriqueSmsOfProf(Long profId);

}
