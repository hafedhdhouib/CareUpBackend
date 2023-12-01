package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.AbonnementUser;

public interface AbonnementUserService {

	public AbonnementUser addAbonnementUser(AbonnementUser abonnementUser);
	
	public List<AbonnementUser> getAllAbonnementUsers(Pageable pageable);

}
