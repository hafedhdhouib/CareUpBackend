package care.up.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import care.up.model.HistoriqueSms;

public interface HistoriqueSmsRepository extends JpaRepositoryImplementation<HistoriqueSms, Long> {
@Query("SELECT COUNT(h) FROM HistoriqueSms h WHERE h.professional.id=?1")
Long countHistoriqueSmsByProf(Long professionalId);
}
