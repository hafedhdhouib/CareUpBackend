package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.PatientDTO;
import care.up.dto.ProfessionalDTO;
import care.up.dto.UserDTO;
import care.up.enums.ProfessionType;
import care.up.message.ResetPasswordRequest;
import care.up.model.User;
import care.up.service.ConsultationRequestService;
import care.up.service.UserService;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ConsultationRequestService requestService;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping("add-patient")
	public ResponseEntity<UserDTO> addUser(@RequestBody PatientDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(User.mapToEntity(dto)));
	}

	@PostMapping("add-professional")
	public ResponseEntity<UserDTO> addUser(@RequestBody ProfessionalDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(User.mapToEntity(dto)));
	}

	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<UserDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<UserDTO> userDTOs = userService.getAllUsers(PageRequest.of(page, size));

		return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
	}

	@PutMapping("edit-patient")
	public ResponseEntity<UserDTO> editUser(@RequestBody PatientDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(User.mapToEntity(dto)));
	}

	@PutMapping("edit-professional")
	public ResponseEntity<UserDTO> editUser(@RequestBody ProfessionalDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.editUser(User.mapToEntity(dto)));
	}

	@GetMapping("reset-password/{phoneNumber}")
	public Boolean resetPassword(@PathVariable(name = "phoneNumber") String phoneNumber) {
		return userService.resetPassword(phoneNumber);
	}

	@GetMapping("check-code/{phoneNumber}/{code}")
	public Boolean checkVerifCode(@PathVariable(name = "phoneNumber") String phoneNumber,
			@PathVariable(name = "code") int code) {
		return userService.checkVerifCode(phoneNumber, code);
	}
	
	@GetMapping("verification/{phoneNumber}/{code}")
	public Boolean verification(@PathVariable(name = "phoneNumber") String phoneNumber,@PathVariable(name = "code") int code) {
		return userService.verfivation(phoneNumber,code);
	}

	
	@PutMapping("change-password")
	public Boolean resetPassword(@RequestBody ResetPasswordRequest request) {
		return userService.editUserPassword(request.getPhoneNumber(), request.getVerifCode(),
				encoder.encode(request.getPassword()));
	}

	@DeleteMapping("delete-user-by-id/{id}")
	public ResponseEntity<Boolean> deleteUserById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
	}

	@GetMapping("get-all-profationnals-of-requests/{patientId}")
	public ResponseEntity<List<ProfessionalDTO>> getProfationnals(@PathVariable(name = "patientId") Long patientId) {
		List<ProfessionalDTO> res = requestService.getAllProfessionalOfAllRequestOfPatient(patientId).stream()
				.map(Professional -> ProfessionalDTO.mapToDTO(Professional)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@GetMapping("get-highest-rated-professionals/{professionType}")
	public ResponseEntity<List<UserDTO>> getHighestRatedProfessionals(
			@PathVariable("professionType") ProfessionType professionType) {
		List<UserDTO> userDTOs = userService.getHighestRatedProfessionals(professionType).stream()
				.map(UserDTO::mapToDTO).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
	}

	@GetMapping("get-accepted-patient-by-professional/{professionalId}/{page}/{size}")
	public ResponseEntity<List<UserDTO>> getAcceptedPatientListByProfessional(
			@PathVariable("professionalId") Long professionalId, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<UserDTO> userDTOs = userService
				.getAcceptedPatientListByProfessional(professionalId, PageRequest.of(page, size)).stream()
				.map(UserDTO::mapToDTO).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
	}
	@GetMapping("get-accepted-professional-by-patient/{patientId}/{page}/{size}")
	public ResponseEntity<List<UserDTO>> getAcceptedProfessionalListByPatient(
			@PathVariable("patientId") Long patientId, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<UserDTO> userDTOs = userService
				.getAcceptedProfessionalListByPatient(patientId, PageRequest.of(page, size)).stream()
				.map(UserDTO::mapToDTO).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(userDTOs);
	}

}
