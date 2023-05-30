package care.up.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import care.up.enums.ProfessionType;
import care.up.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);

	public Optional<User> findByPhoneNumber(String phoneNumber);

	public Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

	public Boolean existsByPhoneNumber(String phoneNumber);

	@Query("SELECT u FROM User u , Rating r WHERE u.id = r.professional.id and u.profession = ?1 GROUP BY r.professional.id ORDER by AVG(r.value) DESC")
	public List<User> getHighestRatedProfessionals(ProfessionType professionType, Pageable pageable);

	@Query("SELECT u FROM User u , ConsultationRequest c WHERE u.id = c.patient.id and c.professional.id = ?1 GROUP BY c.patient.id")
	public List<User> getAcceptedPatientListByProfessional(Long professionalId, Pageable pageable);

	@Query("SELECT u FROM User u , ConsultationRequest c WHERE u.id = c.professional.id and c.patient.id = ?1 GROUP BY c.professional.id")
	public List<User> getAcceptedProfessionalListByPatient(Long PatientId, Pageable pageable);
	

}
