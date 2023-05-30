package care.up.config.webSocket;

import care.up.model.Discussion;

public interface WSService {

	public void sendDiscussionToUser(final String userId, final Discussion discussion);

	public void sendRequestNotificationToUser(final String userId, final Long requestId);
	
	public void sendRequestMessageToUser(final String userId, final Long requestId);
}
