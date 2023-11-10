package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.ConsultationRequestDTO;
import care.up.dto.RequestMatchingDTO;
import care.up.message.LoginRequest;
import care.up.model.ConsultationRequest;
import care.up.model.Professional;
import care.up.model.RequestMatching;
import care.up.model.User;
import care.up.service.ConsultationRequestService;
import care.up.service.RequestMatchingService;

@RequestMapping("/matching")
@RestController
@CrossOrigin
public class RequestMatchingController {

	@Autowired
	RequestMatchingService matchingService;

	@Autowired
	ConsultationRequestService requestService;

	@GetMapping("get-all/{page}/size")
	public ResponseEntity<List<RequestMatchingDTO>> getAllRequestMatching(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size){
		List<RequestMatchingDTO> requestMatchingDTOs = matchingService.getAllRequestsMatching(PageRequest.of(page, size, Sort.by("created").descending()))
				.stream().map(req-> RequestMatchingDTO.mapToDTO(req)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(requestMatchingDTOs);
	}	
	
	@GetMapping("insert/{professionalId}/{reqid}")
	public ResponseEntity<String> insert(
			@PathVariable(name="professionalId") long professionalId,@PathVariable(name="reqid") long reqid){
		System.out.println(matchingService.MatchingRequestWithProfessionalSpe(reqid, professionalId));

	return 	ResponseEntity.status(HttpStatus.OK).body(matchingService.MatchingRequestWithProfessionalSpe(reqid, professionalId));
	}

	@GetMapping("get-by-professionalId/{professionalId}/{page}/{size}")
	public ResponseEntity<List<RequestMatchingDTO>> getAllRequestByProfessionalId(
			@PathVariable(name = "professionalId") Long professionalId, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<RequestMatchingDTO> requestMatchingDTOs = matchingService
				.getAllRequestByProfessionalId(professionalId,
						PageRequest.of(page, size, Sort.by("created").descending()))
				.stream().map(req -> RequestMatchingDTO.mapToDTO(req)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(requestMatchingDTOs);
	}
	@GetMapping("get-by-consultationRequestId/{consultationRequestId}/{page}/{size}")
	public ResponseEntity<List<RequestMatchingDTO>> getAllRequestByConsultationRequestIdId(
			@PathVariable(name = "consultationRequestId") Long consultationRequestId, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<RequestMatchingDTO> requestMatchingDTOs = matchingService
				.getAllRequestByConsultationRequestId(consultationRequestId,
						PageRequest.of(page, size, Sort.by("created").descending()))
				.stream().map(req -> RequestMatchingDTO.mapToDTO(req)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(requestMatchingDTOs);
	}
	

	@GetMapping("get-request-by-id/{requestId}")
	public ResponseEntity<RequestMatchingDTO> getAllRequestByProfessionalId(
			@PathVariable(name = "requestId") Long requestId) {
		RequestMatchingDTO requestMatchingDTO = RequestMatchingDTO.mapToDTO(matchingService.getRequestById(requestId));

		return ResponseEntity.status(HttpStatus.OK).body(requestMatchingDTO);
	}
	

	@PutMapping("accept-request/{professionalId}")
	public ResponseEntity<Object> acceptConsultationRequest(
			@PathVariable(name = "professionalId") Long professionalId, @RequestBody ConsultationRequestDTO dto) {
		ConsultationRequest consultationRequest = requestService.acceptConsultationRequest(professionalId, ConsultationRequest.mapToEntity(dto));
		if (consultationRequest != null) {
			return ResponseEntity.status(HttpStatus.OK).body(ConsultationRequestDTO.mapToDTO(consultationRequest));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request accepted by another professional");
		
	}
	@PutMapping("refuse-request/{requestId}")
	public ResponseEntity<Boolean> refuseConsultationRequest(
			@PathVariable(name = "requestId") Long requestId) {
		return ResponseEntity.status(HttpStatus.OK).body(matchingService.refuseRequestByProfessional(requestId));
		
	}
}
