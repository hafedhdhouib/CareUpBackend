package care.up.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import care.up.enums.ProfessionType;

import org.springframework.data.domain.Page;
import care.up.model.Professional;
import care.up.model.User;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
	
	@Query("SELECT COUNT(p) FROM Professional p WHERE p.profession NOT LIKE '%en_attent%' AND p.profession NOT LIKE '%_desactiver%' AND p.profession NOT LIKE '%_archiver%'")
	Long countProfessionsNotContainingEnAttentOrDesactiverOrArchiver();

	@Query("SELECT COUNT(p) FROM Professional p WHERE p.profession LIKE '%en_attent%'")
	Long countProfessionnelsEnAttent();
	@Query("SELECT COUNT(p) FROM Professional p WHERE p.profession LIKE '%_desactiver%'")
	Long countProfessionnelsDesactiver();
	
	@Query("SELECT COUNT(p) FROM Professional p WHERE p.profession LIKE '%_archiver%'")
	Long countProfessionnelsArchiver();


	
    @Query("SELECT p FROM Professional p WHERE p.profession NOT LIKE '%en_attent%' AND p.profession NOT LIKE '%_desactiver%' AND p.profession NOT LIKE '%_archiver%' ORDER BY p.id DESC")
	public Page<Professional> findProfessionsNotContainingEnAttentOrDesactiverOrArchiver(Pageable pageable);

    @Query("SELECT p FROM Professional p WHERE p.profession LIKE '%_desactiver%'  ORDER BY p.id DESC")
	public Page<Professional> findProfessionnelsDesactiver(Pageable pageable);

    @Query("SELECT p FROM Professional p WHERE p.profession LIKE '%_archiver%'  ORDER BY p.id DESC")
	public Page<Professional> findProfessionnelsArchiver(Pageable pageable);

    
    @Query("SELECT p FROM Professional p WHERE p.profession LIKE '%en_attent%'  ORDER BY p.id DESC")
	public Page<Professional> findProfessionnelsEnAttent(Pageable pageable);

	public Page<Professional> findByProfessionAndAddressCountryAndAddressDelegationAndAddressState(
			ProfessionType profession,
			String country,
			String delegation,
			String state,
			Pageable pageable);
	public Page<Professional> findByProfessionAndAddressCountryAndAddressDelegationAndAddressStateAndGenre(
			ProfessionType profession,
			String country,
			String delegation,
			String state,
			String genre,
			Pageable pageable);
	
	public Page<Professional> findByProfessionAndAddressCountryAndAddressDelegation(
			@NotNull ProfessionType type,
			@NotNull String country,
			@NotNull String delegation,
			Pageable pageable);
	
	public Page<Professional> findByProfessionAndAddressCountryAndAddressDelegationAndGenre(
			@NotNull ProfessionType type,
			@NotNull String country,
			@NotNull String delegation,
			@NotNull String genre,
			Pageable pageable);

	public Page<Professional> findByProfessionAndAddressCountry(
			@NotNull ProfessionType type,
			@NotNull String country,
			Pageable pageable);
	
	public Page<Professional> findByProfessionAndAddressState(
			@NotNull ProfessionType type,
			@NotNull String state,
			Pageable pageable);	

	
	public List<Professional> findByAddressDelegation(
			@NotNull String delegation);
	
	public List<Professional> findByProfessionAndAddressDelegation(
			@NotNull ProfessionType type,
			@NotNull String delegation);

	public List<Professional>findByAddressState(
			@NotNull String state);
	
	public Page<Professional> findByProfessionAndAddressCountryAndGenre(@NotNull ProfessionType type,
			@NotNull String country, @NotNull String genre, Pageable pageable);
	
	public List<User> findByProfession(@NotNull ProfessionType type);

}
