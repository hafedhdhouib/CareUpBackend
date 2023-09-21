package care.up.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.dto.UserDTO;
import care.up.enums.ProfessionType;
import care.up.message.SMSClass;
import care.up.model.Patient;
import care.up.model.Professional;
import care.up.model.User;
import care.up.repository.PatientRepository;
import care.up.repository.ProfessionalRepository;
import care.up.repository.UserRepository;
import care.up.service.SmsService;
import care.up.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	ProfessionalRepository professionalRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SmsService smsService;

	@Override
	public UserDTO addUser(User user) {
		user.setUsername(user.getName().concat("-".concat(user.getPhoneNumber())));
		user.setVerifCode(new Random().nextInt(999999 - 111111 + 1) + 111111);
		if (user instanceof Professional) {
			return UserDTO.mapToDTO(professionalRepository.save(((Professional) user)));
		} else if (user instanceof Patient) {
			return UserDTO.mapToDTO(patientRepository.save(((Patient) user)));
		}
		return null;
	}

	@Override
	public List<UserDTO> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable).stream().map(UserDTO::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return UserDTO.mapToDTO(user.get());
		}
		return null;
	}

	@Override
	public List<User> getByName(String name) {
	return userRepository.getByName(name);
	}
	
	@Override
	public List<Professional> getProByName(String name) {
	return userRepository.getProByName(name);
	}
	
	@Override
	public UserDTO editUser(User user) {
		Optional<User> myUser = userRepository.findById(user.getId());
		if (myUser.isPresent()) {
			User toEdit = myUser.get();
			if (toEdit instanceof Professional) {
				Professional.patch((Professional) user, (Professional) toEdit);
				return UserDTO.mapToDTO(professionalRepository.save(((Professional) toEdit)));
			} else if (toEdit instanceof Patient) {
				Patient.patch((Patient) user, (Patient) toEdit);
				return UserDTO.mapToDTO(patientRepository.save(((Patient) toEdit)));
			}
		}
		return null;
	}

	@Override
	public Boolean deleteUserById(Long id) {
		if (id != null && userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return !userRepository.existsById(id);
		}
		return false;
	}

	@Override
	public Boolean resetPassword(String phoneNumber) {
		Optional<User> myUser = userRepository.findByPhoneNumber(phoneNumber);
		if (myUser.isPresent()) {
			int generatedCode = new Random().nextInt(999999 - 111111 + 1) + 111111;
			User toEdit = myUser.get();
			toEdit.setVerifCode(generatedCode);
			if (toEdit instanceof Professional) {
				professionalRepository.save(((Professional) toEdit));
			} else if (toEdit instanceof Patient) {
				patientRepository.save(((Patient) toEdit));
			}
			SMSClass sms = new SMSClass(toEdit.getPhoneNumber(), "votre code careUp est: " + generatedCode);
			smsService.sendSMS(sms);
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkVerifCode(String phoneNumber, int code) {
		Optional<User> myUser = userRepository.findByPhoneNumber(phoneNumber);
		if (myUser.isPresent() && myUser.get().getVerifCode() == code) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean editUserPassword(String phoneNumber, int code, String password) {
		Optional<User> myUser = userRepository.findByPhoneNumber(phoneNumber);
		if (myUser.isPresent() && myUser.get().getVerifCode() == code) {
			User toEdit = myUser.get();
			toEdit.setPassword(password);
			if (toEdit instanceof Professional) {
				professionalRepository.save(((Professional) toEdit));
			} else if (toEdit instanceof Patient) {
				patientRepository.save(((Patient) toEdit));
			}
			return true;
		}
		return false;
	}

	@Override
	public List<User> getHighestRatedProfessionals(ProfessionType professionType) {
		return userRepository.getHighestRatedProfessionals(professionType, PageRequest.of(0, 10));
	}

	@Override
	public List<User> getAcceptedPatientListByProfessional(Long professionalId,  Pageable pageable) {
		return userRepository.getAcceptedPatientListByProfessional(professionalId, pageable);
	}
	@Override
	public List<User> getAcceptedProfessionalListByPatient(Long PatientId,  Pageable pageable) {
		return userRepository.getAcceptedProfessionalListByPatient(PatientId, pageable);
	}
	
	


}
