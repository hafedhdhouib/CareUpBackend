package care.up.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.dto.ProfessionalDTO;
import care.up.dto.UserDTO;
import care.up.enums.ProfessionType;
import care.up.model.Professional;
import care.up.model.User;
import care.up.repository.ProfessionalRepository;
import care.up.service.ProfessionalService;
@Service
@Transactional
public class ProfessionalServiceImpl implements ProfessionalService {
	@Autowired
	ProfessionalRepository professionalRepository;

	@Override
	public List<ProfessionalDTO> getAllPro(Pageable pageable) {
return  professionalRepository.findAll(pageable).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());	}
	@Override
	public Number countProfessional() {
		return professionalRepository.count();
	}
	@Override
	public List<User> getByProfessional(ProfessionType type) {
		return professionalRepository.findByProfession(type);
	}


}
