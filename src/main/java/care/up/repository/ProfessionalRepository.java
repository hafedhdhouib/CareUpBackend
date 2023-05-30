package care.up.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import care.up.enums.ProfessionType;
import care.up.model.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

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

	public Page<Professional> findByProfessionAndAddressCountryAndAddressDelegation(@NotNull ProfessionType type,
			@NotNull String country, @NotNull String delegation, Pageable pageable);
	
	public Page<Professional> findByProfessionAndAddressCountryAndAddressDelegationAndGenre(@NotNull ProfessionType type,
			@NotNull String country, @NotNull String delegation, @NotNull String genre, Pageable pageable);

	
	public Page<Professional> findByProfessionAndAddressCountry(@NotNull ProfessionType type,
			@NotNull String country, Pageable pageable);
	
	public Page<Professional> findByProfessionAndAddressCountryAndGenre(@NotNull ProfessionType type,
			@NotNull String country, @NotNull String genre, Pageable pageable);
}
