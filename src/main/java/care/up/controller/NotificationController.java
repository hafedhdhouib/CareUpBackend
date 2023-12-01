package care.up.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import care.up.dto.NotificationDTO;
import care.up.model.Notification;
import care.up.service.NotificationService;

@RequestMapping("/notification")
@RestController
@CrossOrigin
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@GetMapping("get-notif-of-user/{userId}")
	public ResponseEntity<List<NotificationDTO>> getAllNotificationOfUser(@PathVariable(name = "userId") Long userId) {

		List<NotificationDTO> dtos = new ArrayList<>();
		List<Notification> res = notificationService.getAllNotificationOfUser(userId);
		if (res != null && !res.isEmpty()) {
			dtos = res.stream().map(m -> NotificationDTO.mapToDTO(m)).collect(Collectors.toList());
			Collections.reverse(dtos);
		}

		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("mark-all-as-seen/{userId}")
	public ResponseEntity<Boolean> markAllNotificationOfUserAsSeen(@PathVariable(name = "userId") Long userId) {
		Boolean res = notificationService.markAllNotificationOfUserAsSeen(userId);
		if (res) {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}

	@DeleteMapping("delete-notif/{id}")
	public ResponseEntity<Boolean> deleteNotification(@PathVariable(name = "id") Long id) {
		Boolean res = notificationService.deleteNotification(id);
		if (res) {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}
}
