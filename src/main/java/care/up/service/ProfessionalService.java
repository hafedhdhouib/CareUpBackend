package care.up.service;

import java.util.List;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

import care.up.dto.ProfessionalDTO;
import care.up.dto.UserDTO;
import care.up.enums.ProfessionType;
import care.up.model.Professional;
import care.up.model.User;

public interface ProfessionalService {
	
	public List<ProfessionalDTO> getAllPro(Pageable pageable);
	public Number countProfessional();
	public List<User> getByProfessional(ProfessionType type);

}