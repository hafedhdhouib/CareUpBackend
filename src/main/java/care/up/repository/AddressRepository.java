package care.up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
