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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.AbonnementDTO;
import care.up.dto.AbonnementUserDTO;
import care.up.model.AbonnementUser;
import care.up.service.AbonnementService;
import care.up.service.AbonnementUserService;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("/abonnement-user")
@RestController
@CrossOrigin
public class AbonnementUserController {
	@Autowired
	AbonnementUserService abonnementUserService;

	
	@GetMapping("get-all/{page}/{size}")
	public ResponseEntity<List<AbonnementUserDTO>> getAllwithPaginate(@PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size){
		List<AbonnementUserDTO> dtos=abonnementUserService.getAllAbonnementUsers(PageRequest.of(page, size)).stream()
				.map(AbonnementUserDTO::mapToDTO).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(dtos);
			}
	@PostMapping("add")
	public  ResponseEntity<AbonnementUserDTO>addAbonnmentUser(@RequestBody AbonnementUserDTO dto){
		System.out.println(dto.toString());
		return ResponseEntity.status(HttpStatus.OK)
				.body(AbonnementUserDTO.mapToDTO(
				abonnementUserService.addAbonnementUser(AbonnementUser.mapToEntity(dto))));
		
	}
}
