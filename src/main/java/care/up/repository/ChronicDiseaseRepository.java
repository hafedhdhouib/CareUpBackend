package care.up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.ChronicDisease;

public interface ChronicDiseaseRepository extends JpaRepository<ChronicDisease, Long>{

}
