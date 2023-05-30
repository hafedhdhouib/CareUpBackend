package care.up.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.DiscussionDTO;
import care.up.model.Discussion;
import care.up.service.DiscussionService;

@RequestMapping("/disc")
@RestController
@CrossOrigin
public class DiscussionController {

	@Autowired
	DiscussionService discussionService;

	@PreAuthorize("#dto.firstUser.id == principal.id or #dto.secondUser.id == principal.id")
	@PostMapping("new")
	public ResponseEntity<DiscussionDTO> addNewDiscussion(@RequestBody DiscussionDTO dto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(DiscussionDTO.mapToDTO(discussionService.addNewDiscussion(Discussion.mapToEntity(dto))));
	}

	@PreAuthorize("#userId == principal.id")
	@GetMapping("get-by-user-id/{userId}/{page}/{size}")
	public ResponseEntity<List<DiscussionDTO>> getByUserId(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "page") int page, @PathVariable(name = "size") int size) {
		List<DiscussionDTO> dtos = discussionService
				.getAllByUserId(userId, PageRequest.of(page, size, Sort.by("modified").descending())).stream()
				.map(d -> DiscussionDTO.mapToDTO(d)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@DeleteMapping("delete-by-id/{id}}")
	public boolean deleteDiscussionById(@PathVariable(name = "id") Long id) {
		return discussionService.deleteDiscussionById(id);
	}

	@PreAuthorize("#user1Id == principal.id or #user2Id == principal.id")
	@GetMapping("exists-between/{user1Id}/{user2Id}")
	public boolean existsDiscussionBetween(@PathVariable(name = "user1Id") Long user1Id,
			@PathVariable(name = "user2Id") Long user2Id) {
		return discussionService.existsDiscussionBetween(user1Id, user2Id);
	}

	@PreAuthorize("#user1Id == principal.id or #user2Id == principal.id")
	@GetMapping("get-between/{user1Id}/{user2Id}/{page}/{size}")
	public ResponseEntity<DiscussionDTO> getByUserId(@PathVariable(name = "user1Id") Long user1Id,
			@PathVariable(name = "user2Id") Long user2Id, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(DiscussionDTO.mapToDTO(discussionService.getDiscussionBetween(user1Id, user2Id)));
	}
}
