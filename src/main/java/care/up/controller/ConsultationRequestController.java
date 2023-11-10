package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.ConsultationRequestDTO;
import care.up.dto.ProfessionalDTO;
import care.up.model.ConsultationRequest;
import care.up.model.Professional;
import care.up.service.ConsultationRequestService;

@RequestMapping("/request")
@RestController
@CrossOrigin
public class ConsultationRequestController {

	@Autowired
	ConsultationRequestService requestService;

	@PostMapping("create-request")
	public ResponseEntity<ConsultationRequestDTO> addConsultationRequest(
			@RequestBody ConsultationRequestDTO consultationRequestDTO) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ConsultationRequestDTO.mapToDTO(
				requestService.addConsultationRequest(ConsultationRequest.mapToEntity(consultationRequestDTO))));
	}

	@GetMapping("get-count-request")
	public long getCountRequest() {
		return requestService.countConsultationRequest();
	}
	
	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<ConsultationRequestDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<ConsultationRequestDTO> consultationRequestDTOs = requestService
				.getAllConsultationRequests(PageRequest.of(page, size)).stream()
				.map(req -> ConsultationRequestDTO.mapToDTO(req)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(consultationRequestDTOs);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<ConsultationRequestDTO> getConsultationRequestById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ConsultationRequestDTO.mapToDTO(requestService.getConsultationRequestById(id)));
	}

	@GetMapping("get-all-by-patient-id/{id}")
	public ResponseEntity<List<ConsultationRequestDTO>> getAllByPatientId(@PathVariable(name = "id") Long id) {
		List<ConsultationRequestDTO> consultationRequestDTOs = requestService.getAllByPatientId(id).stream()
				.map(req -> ConsultationRequestDTO.mapToDTO(req)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(consultationRequestDTOs);
	}

	@PutMapping("edit-request")
	public ResponseEntity<ConsultationRequestDTO> editConsultationRequest(@RequestBody ConsultationRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(ConsultationRequestDTO
				.mapToDTO(requestService.editConsultationRequest(ConsultationRequest.mapToEntity(dto))));
	}

	@DeleteMapping("delete-by-id/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(requestService.deleteConsultationRequestById(id));
	}

	@GetMapping("testsendNotif/{id}")
	public List<Professional> testsendNotif(@PathVariable(name = "id") Long id) {
		return requestService.testSendNotif(id);
	}

	@GetMapping("get-all-profationnals-of-requests/{patientId}")
	public ResponseEntity<List<ProfessionalDTO>> getProfationnals(@PathVariable(name = "patientId") Long patientId) {
		List<ProfessionalDTO> res = requestService.getAllProfessionalOfAllRequestOfPatient(patientId).stream()
				.map(Professional -> ProfessionalDTO.mapToDTO(Professional)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

}
