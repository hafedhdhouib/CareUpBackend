package care.up.controller;

import java.sql.SQLIntegrityConstraintViolationException;
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

import care.up.dto.RequirementDTO;
import care.up.model.Requirement;
import care.up.service.RequirementService;

@RequestMapping("/requirement")
@RestController
@CrossOrigin
public class RequirementController {

	@Autowired
	RequirementService requirementService;

	@PostMapping("create")
	public ResponseEntity<?> addRequirement(@RequestBody RequirementDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(RequirementDTO.mapToDTO(requirementService.addRequirement(Requirement.mapToEntity(dto))));
		} catch (SQLIntegrityConstraintViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<RequirementDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<RequirementDTO> dtos = requirementService.getAllRequirements(PageRequest.of(page, size)).stream()
				.map(RequirementDTO::mapToDTO).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<RequirementDTO> getConsultationRequestById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(RequirementDTO.mapToDTO(requirementService.getRequirementById(id)));
	}

	@PutMapping("edit-request")
	public ResponseEntity<RequirementDTO> editConsultationRequest(@RequestBody RequirementDTO dto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(RequirementDTO.mapToDTO(requirementService.editRequirement(Requirement.mapToEntity(dto))));
	}

	@DeleteMapping("delete-by-id/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(requirementService.deleteRequirementById(id));
	}

}
