package care.up.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import care.up.enums.ProfessionType;
import care.up.model.Professional;
import care.up.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);

	public Optional<User> findByPhoneNumber(String phoneNumber);

	public Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
	

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

	public Boolean existsByPhoneNumber(String phoneNumber);
	
	@Query("SELECT u FROM User u")
	public List<?> getAllProfessionals(Pageable pageable);

	@Query("SELECT u FROM User u , Rating r WHERE u.id = r.professional.id and u.profession = ?1 GROUP BY r.professional.id ORDER by AVG(r.value) DESC")
	public List<User> getHighestRatedProfessionals(ProfessionType professionType, Pageable pageable);

	@Query("SELECT u FROM User u , ConsultationRequest c WHERE u.id = c.patient.id and c.professional.id = ?1 GROUP BY c.patient.id")
	public List<User> getAcceptedPatientListByProfessional(Long professionalId, Pageable pageable);

	@Query("SELECT u FROM User u , ConsultationRequest c WHERE u.id = c.professional.id and c.patient.id = ?1 GROUP BY c.professional.id")
	public List<User> getAcceptedProfessionalListByPatient(Long PatientId, Pageable pageable);
	
	@Query("SELECT a.delegation, COUNT(*) AS count FROM Address a, Professional p WHERE p.id = a.id GROUP BY a.delegation ORDER by a.delegation")
    public List<?> statistiqueDeligationPro();
	@Query("SELECT u.genre, p.profession, a.delegation ,COUNT(*) AS countpro FROM Address a, User u, Professional p WHERE a.id=p.id and u.id = p.id group BY a.delegation, u.genre , p.profession ORDER BY a.delegation")
    public List<?> statistiqueGenreProAndDeligation();
	@Query("SELECT u.genre, a.delegation, COUNT(*) AS count FROM Address a,User u, Patient p WHERE p.id = a.id AND a.id=u.id GROUP BY a.delegation, u.genre ORDER BY a.delegation")
    public List<?> statistiqueDeligationPatient();
	
	@Query("SELECT u FROM User u, Patient p WHERE u.name LIKE %?1% and u.id = p.id")
	public List<User> getByName(String name);
	
	@Query("SELECT u FROM User u, Professional p WHERE u.name LIKE %?1% and u.id = p.id")
	public List<Professional> getProByName(String name);


	
}
