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

import care.up.dto.ChronicDiseaseDTO;
import care.up.model.ChronicDisease;
import care.up.service.ChronicDiseaseService;

@RequestMapping("/chronic-disease")
@RestController
@CrossOrigin
public class ChronicDiseaseController {

	@Autowired
	ChronicDiseaseService chronicDiseaseService;

	@PostMapping("create")
	public ResponseEntity<?> addChronicDisease(@RequestBody ChronicDiseaseDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ChronicDiseaseDTO
					.mapToDTO(chronicDiseaseService.addChronicDisease(ChronicDisease.mapToEntity(dto))));
		} catch (SQLIntegrityConstraintViolationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<ChronicDiseaseDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<ChronicDiseaseDTO> dtos = chronicDiseaseService.getAllChronicDiseases(PageRequest.of(page, size)).stream()
				.map(ChronicDiseaseDTO::mapToDTO).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("get-by-id/{id}")
	public ResponseEntity<ChronicDiseaseDTO> getChronicDiseaseDTOById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ChronicDiseaseDTO.mapToDTO(chronicDiseaseService.getChronicDiseaseById(id)));
	}

	@PutMapping("edit-request")
	public ResponseEntity<ChronicDiseaseDTO> editChronicDisease(@RequestBody ChronicDiseaseDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(
				ChronicDiseaseDTO.mapToDTO(chronicDiseaseService.editChronicDisease(ChronicDisease.mapToEntity(dto))));
	}

	@DeleteMapping("delete-by-id/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(chronicDiseaseService.deleteChronicDiseaseById(id));
	}
}
