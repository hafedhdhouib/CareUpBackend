package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.dto.UserDTO;
import care.up.enums.ProfessionType;
import care.up.model.Professional;
import care.up.model.User;

public interface UserService {

	public UserDTO addUser(User user);

	public List<UserDTO> getAllUsers(Pageable pageable);
	

	public UserDTO getUserById(Long id);
	
	public List<User> getByName(String name);
	
	public List<Professional> getProByName(String name);


	public UserDTO editUser(User user);
	
	public Boolean resetPassword(String phoneNumber);
	
	public Boolean checkVerifCode(String phoneNumber, int code);

	public Boolean editUserPassword(String phoneNumber, int code, String password);

	public Boolean deleteUserById(Long id);
	
	public List<User> getHighestRatedProfessionals(ProfessionType professionType);
	
	public List<User> getAcceptedPatientListByProfessional(Long professionalId, Pageable pageable);
	
	public List<User> getAcceptedProfessionalListByPatient(Long PatientId,  Pageable pageable);

	public Boolean verfivation(String phoneNumber, int code);
	public List<?> statistiqueDeligationPro();
	public List<?> statistiqueGenreProAndDeligation();
	public List<?> statistiqueDeligationPatient();


}
