package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import care.up.dto.PatientDTO;
import care.up.dto.ProfessionalDTO;
import care.up.dto.UserDTO;
import care.up.enums.ProfessionType;
import care.up.message.JwtResponse;
import care.up.message.LoginRequest;
import care.up.model.Patient;
import care.up.model.User;
import care.up.repository.UserRepository;
import care.up.security.jwt.JwtProvider;
import care.up.security.jwt.service.UserPrinciple;
import care.up.service.FilesStorageService;
import care.up.service.SmsService;
import care.up.service.UserConversionService;
import care.up.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	UserConversionService userConversionService;


	@Autowired
	FilesStorageService filesStorageService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	SmsService smsService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateJwtToken(authentication);

		UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		UserDTO userDTO = userService.getUserById(userDetails.getId());
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDTO, roles));
	}

		@PostMapping("/patient-conversion-pro")
	    public ResponseEntity<?> convertPatientToProfessional(@RequestParam String patient,@RequestParam("cvFile") MultipartFile cvfile, @RequestParam("pro")String pro) throws JsonMappingException, JsonProcessingException {
		String diplome=	filesStorageService.save(cvfile);
		System.out.print(pro);

			Patient dto = new ObjectMapper().readValue(patient, Patient.class);
			if(userConversionService.convertPatientToProfessional(dto,diplome,ProfessionType.valueOf(pro))!=null)
			{
				return ResponseEntity.ok("User registered successfully!");
			}
			
			return ResponseEntity.badRequest().body("Error: Check your info!");
					
		}
	
	@PostMapping("/signup-as-professional")
	public ResponseEntity<?> registerUser(@RequestParam(name = "professional") String professionalInfo,
			@RequestParam("cvFile") MultipartFile cvFile) {
		try {

			ProfessionalDTO dto = new ObjectMapper().readValue(professionalInfo, ProfessionalDTO.class);
			if (Boolean.TRUE
					.equals(userRepository.existsByUsername(dto.getName().concat("-".concat(dto.getPhoneNumber()))))) {
				return ResponseEntity.badRequest().body("Error: Username is already taken!");
			}

			if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(dto.getPhoneNumber()))) {
				return ResponseEntity.badRequest().body("Error: Phone number is already taken!");
			}

			if (Boolean.TRUE.equals(userRepository.existsByEmail(dto.getEmail()))) {
				return ResponseEntity.badRequest().body("Error: Email is already in use!");
			}
			dto.setPassword(encoder.encode(dto.getPassword()));
			dto.setDiploma(filesStorageService.save(cvFile));
			if (userService.addUser(User.mapToEntity(dto)) != null) {
				return ResponseEntity.ok("User registered successfully!");
			}

			return ResponseEntity.badRequest().body("Error: Check your info!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error: Check your info : " + e.getMessage() + "!");
		}

	}

	@PostMapping("/signup-as-patient")
	public ResponseEntity<?> registerUser(@RequestBody PatientDTO dto) {
		if (Boolean.TRUE
				.equals(userRepository.existsByUsername(dto.getName().concat("-".concat(dto.getPhoneNumber()))))) {
			return ResponseEntity.badRequest().body("Error: Username is already taken!");
		}

		if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(dto.getPhoneNumber()))) {
			return ResponseEntity.badRequest().body("Error: Phone number is already taken!");
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(dto.getEmail()))) {
			return ResponseEntity.badRequest().body("Error: Email is already in use!");
		}
		dto.setPassword(encoder.encode(dto.getPassword()));
		if (userService.addUser(User.mapToEntity(dto)) != null) {
			return ResponseEntity.ok("User registered successfully!");
		}

		return ResponseEntity.badRequest().body("Error: Check your info!");

	}
	
//	@PostMapping("send-sms")
//	public boolean sendSMS(@RequestBody SMSClass smsClass) {
//		return smsService.sendSMS(smsClass);
//	}
}
