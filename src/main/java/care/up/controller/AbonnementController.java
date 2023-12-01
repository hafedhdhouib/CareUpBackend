package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.AbonnementDTO;
import care.up.service.AbonnementService;

@RequestMapping("/abonnement")
@RestController
@CrossOrigin
public class AbonnementController {
	
	@Autowired
	AbonnementService abonnementService;
	
	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<AbonnementDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size){
		List<AbonnementDTO> dtos=abonnementService.getAllAbonnement(PageRequest.of(page, size)).stream()
				.map(AbonnementDTO::mapToDTO).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(dtos);

}
	}
