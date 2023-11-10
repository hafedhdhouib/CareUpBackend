package care.up.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.AbonementUserKey;
import care.up.model.AbonnementUser;

public interface AbonnementUserRepository  extends JpaRepository<AbonnementUser, AbonementUserKey> {

}
