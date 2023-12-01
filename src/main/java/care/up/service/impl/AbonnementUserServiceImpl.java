package care.up.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.model.Abonnement;
import care.up.model.AbonnementUser;
import care.up.model.User;
import care.up.repository.AbonnementRepository;
import care.up.repository.AbonnementUserRepository;
import care.up.repository.UserRepository;
import care.up.service.AbonnementUserService;
@Service
public class AbonnementUserServiceImpl implements AbonnementUserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AbonnementRepository abonnementRepository;

	@Autowired
	AbonnementUserRepository abonnementUserRepository;

	@Override
	public AbonnementUser addAbonnementUser(AbonnementUser abonnementUser) {
		AbonnementUser res = null;
		System.out.print(abonnementUser.getUser());
		if (abonnementUser != null) {
			Optional<User> user = userRepository.findById(abonnementUser.getUser().getId());
			if (user.isPresent()) {
				abonnementUser.setUser(user.get());
			}
			Optional<Abonnement> abonnement = abonnementRepository.findById(abonnementUser.getAbonnement().getId());
			if (abonnement.isPresent()) {
				abonnementUser.setAbonnement(abonnement.get());
			}
		res= abonnementUserRepository.save(abonnementUser);}
		
		return res;
	}

	@Override
	public List<AbonnementUser> getAllAbonnementUsers(Pageable pageable) {
		// TODO Auto-generated method stub
		return abonnementUserRepository.findAll(pageable).toList();	}

	}

