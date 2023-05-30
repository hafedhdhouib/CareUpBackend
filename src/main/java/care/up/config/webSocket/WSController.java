package care.up.config.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wsChat")
@RestController
public class WSController {

	@Autowired
	private WSService service;

//	@PostMapping("/send-private-message/{id}")
//	public void sendPrivateMessage(@PathVariable final String id, @RequestBody final ResponseMessage message) {
//		service.notifyUser(id, message.getMessage());
//	}
	
	@GetMapping("/send-to/{id}/{requestId}")
	public void sendNotificationToUser(@PathVariable(name = "id") final String id, @PathVariable(name = "requestId") final Long requestId) {
		service.sendRequestNotificationToUser(id, requestId);
	}
	
	@GetMapping("/send-message-to/{id}/{requestId}")
	public void sendMessageToUser(@PathVariable(name = "id") final String id, @PathVariable(name = "requestId") final Long requestId) {
		service.sendRequestMessageToUser(id, requestId);
	}
}
