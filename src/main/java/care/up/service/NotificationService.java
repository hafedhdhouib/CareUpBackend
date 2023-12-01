package care.up.service;

import java.util.List;

import care.up.model.ConsultationRequest;
import care.up.model.Notification;

public interface NotificationService {

	public Notification createNotoification(Notification notification);
	
	public Notification createNotoificationForAcceptedRequest(ConsultationRequest request);

	public List<Notification> getAllNotificationOfUser(Long userId);

	public Boolean markAllNotificationOfUserAsSeen(Long userId);
	
	public boolean deleteNotification(Long id);
}
