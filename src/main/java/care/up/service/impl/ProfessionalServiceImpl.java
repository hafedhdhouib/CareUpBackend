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
	
	@Override
	public List<ProfessionalDTO>getprobydelegation(String delegation){
		return professionalRepository.findByAddressDelegation(delegation).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());
	}
	@Override
	public List<ProfessionalDTO> getByProfessionAndAddressDelegation(ProfessionType type, String delegation) {
		return professionalRepository.findByProfessionAndAddressDelegation(type, delegation).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());
	}
	@Override
	public Page<Professional> findByProfessionAndAddressState(ProfessionType type, String state, Pageable pageable) {
		return  professionalRepository.findByProfessionAndAddressState(type, state, pageable);
	}
	@Override
	public List<ProfessionalDTO> getByState(String state) {
		return professionalRepository.findByAddressState(state).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());
	}
	@Override
	public List<ProfessionalDTO> getProfessionsNotContainingEnAttentOrDesactiverOrArchiver(Pageable pageable) {
		return  professionalRepository.findProfessionsNotContainingEnAttentOrDesactiverOrArchiver(pageable).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());	}
	

	@Override
	public Long countProfessionsNotContainingEnAttentOrDesactiverOrArchiver() {
	return professionalRepository.countProfessionsNotContainingEnAttentOrDesactiverOrArchiver();
}
	@Override
	public List<ProfessionalDTO> getProfessionnelsEnAttent(Pageable pageable) {
		return  professionalRepository.findProfessionnelsEnAttent(pageable).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());	}

	@Override
	public Long countProfessionnelsEnAttent() {
		return professionalRepository.countProfessionnelsEnAttent();
	}
	
	@Override
	public List<ProfessionalDTO> getProfessionnelsDesactiver(Pageable pageable) {
		return  professionalRepository.findProfessionnelsDesactiver(pageable).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());	}

	@Override
	public Long countProfessionnelsDesactiver() {
		return professionalRepository.countProfessionnelsDesactiver();
	}
	@Override
	public List<ProfessionalDTO> getProfessionnelsArchiver(Pageable pageable) {
		return  professionalRepository.findProfessionnelsArchiver(pageable).stream().map(ProfessionalDTO::mapToDTO).collect(Collectors.toList());	}

	@Override
	public Long countProfessionnelsArchiver() {
		return professionalRepository.countProfessionnelsArchiver();
	}
 

}
