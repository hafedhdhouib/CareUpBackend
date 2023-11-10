package care.up.service;

import java.util.List;

import javax.validation.constraints.NotNull;

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
	public List<ProfessionalDTO> getprobydelegation(String delegation);
	public List<ProfessionalDTO>getByProfessionAndAddressDelegation(ProfessionType type,String delegation);
	public List<ProfessionalDTO>getByState(String state);

	public Page<Professional> findByProfessionAndAddressState(ProfessionType type,String state,Pageable pageable);	
	
	public Long countProfessionsNotContainingEnAttentOrDesactiverOrArchiver();
	public List<ProfessionalDTO> getProfessionsNotContainingEnAttentOrDesactiverOrArchiver(Pageable pageable);
	
	public Long countProfessionnelsEnAttent();
	public List<ProfessionalDTO> getProfessionnelsEnAttent(Pageable pageable);
	
	public Long countProfessionnelsDesactiver();
	public List<ProfessionalDTO> getProfessionnelsDesactiver(Pageable pageable);

	public Long countProfessionnelsArchiver();
	public List<ProfessionalDTO> getProfessionnelsArchiver(Pageable pageable);

}