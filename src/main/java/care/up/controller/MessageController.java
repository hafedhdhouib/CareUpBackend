package care.up.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.MessageDTO;
import care.up.model.Message;
import care.up.service.MessageService;

@RequestMapping("/chat")
@RestController
@CrossOrigin
public class MessageController {

	@Autowired
	MessageService messageService;

	@PreAuthorize("#dto.sender.id == principal.id")
	@PostMapping("send-message")
	public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO dto) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(MessageDTO.mapToDTO(messageService.sendMessage(Message.mapToEntity(dto))));
	}

	@PreAuthorize("(#user1Id == principal.id) or (#user2Id == principal.id)")
	@GetMapping("get-chat-between/{firstId}/{secondId}/{page}/{size}")
	public ResponseEntity<List<MessageDTO>> getChatBetween(@PathVariable(name = "firstId") Long user1Id,
			@PathVariable(name = "secondId") Long user2Id, @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {

		List<MessageDTO> dtos = new ArrayList<>();
		List<Message> res = messageService.getMessagesBetween(user1Id, user2Id,
				PageRequest.of(page, size, Sort.by("created").descending()));
		if (res != null && !res.isEmpty()) {
			dtos = res.stream().map(m -> MessageDTO.mapToDTO(m)).collect(Collectors.toList());
			Collections.reverse(dtos);
		}

		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("get-chat-of-disc/{discId}/{page}/{size}")
	public ResponseEntity<List<MessageDTO>> getChatOfDiscussion(@PathVariable(name = "discId") Long discId,
			@PathVariable(name = "page") int page, @PathVariable(name = "size") int size) {

		List<MessageDTO> dtos = new ArrayList<>();
		List<Message> res = messageService.getMessagesOfDiscussion(discId,
				PageRequest.of(page, size, Sort.by("created").descending()));
		if (res != null && !res.isEmpty()) {
			dtos = res.stream().map(m -> MessageDTO.mapToDTO(m)).collect(Collectors.toList());
			Collections.reverse(dtos);
		}

		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}
	
	@GetMapping("messages-seen/{discId}")
	public void changeAllMessageOfDiscToSeen(@PathVariable(name = "discId") Long discId) {
		messageService.changeAllMessageOfDiscToSeen(discId);
	}
}
