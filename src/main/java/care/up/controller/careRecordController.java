package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.CareRecordDTO;
import care.up.model.CareRecord;
import care.up.service.CareRecordService;

@RequestMapping("/care-record")
@RestController
@CrossOrigin
public class careRecordController {

	@Autowired
	CareRecordService careRecordService;

	@PostMapping("add-record")
	public ResponseEntity<CareRecordDTO> addRecord(@RequestBody CareRecordDTO dto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(CareRecordDTO.mapToDTO(careRecordService.addCareRecord(CareRecord.mapToEntity(dto))));
	}

	@GetMapping("get-records/{professionalId}/{patientId}/{page}/{size}")
	public ResponseEntity<List<CareRecordDTO>> getRecordswithPaginate(
			@PathVariable(name = "professionalId") Long professionalId,
			@PathVariable(name = "patientId") Long patientId, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		List<CareRecordDTO> RecordDTOs = careRecordService
				.getPatientCareRecordsWithSpecProfessional(professionalId, patientId,
						PageRequest.of(page, size, Sort.by("created").descending()))
				.stream().map(req -> CareRecordDTO.mapToDTO(req)).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(RecordDTOs);
	}

	@DeleteMapping("delete-by-id/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(careRecordService.deleteCareRecord(id));
	}
}
