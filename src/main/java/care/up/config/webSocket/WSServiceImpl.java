package care.up.config.webSocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import care.up.dto.DiscussionDTO;
import care.up.model.Discussion;

@Service
public class WSServiceImpl implements WSService {

	private final SimpMessagingTemplate messagingTemplate;

	public WSServiceImpl(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void sendDiscussionToUser(String userId, Discussion discussion) {
		messagingTemplate.convertAndSend("/topic/" + userId, DiscussionDTO.mapToDTO(discussion));

	}

	@Override
	public void sendRequestNotificationToUser(String userId, Long requestId) {
		RequestNotification requestNotification = new RequestNotification(
				"vous avez une nouvelle notification", requestId);

		messagingTemplate.convertAndSend("/notification/" + userId, requestNotification);

	}
	
	@Override
	public void sendRequestMessageToUser(String userId, Long requestId) {
		RequestNotification requestNotification = new RequestNotification(
				"vous avez un nouveau message", requestId);

		messagingTemplate.convertAndSend("/topic/" + userId, requestNotification);

	}
}
