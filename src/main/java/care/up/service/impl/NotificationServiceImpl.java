package care.up.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import care.up.model.ConsultationRequest;
import care.up.model.Notification;
import care.up.model.User;
import care.up.repository.NotificationRepository;
import care.up.repository.UserRepository;
import care.up.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Notification createNotoification(Notification notification) {
		if (notification != null) {
			Optional<User> optional = userRepository.findById(notification.getUser().getId());
			if (optional.isPresent()) {
				notification.setSeen(false);
				notification.setUser(optional.get());
				return notificationRepository.save(notification);
			}

		}
		return null;
	}

	@Override
	public List<Notification> getAllNotificationOfUser(Long userId) {
		if (userId != null) {
			return notificationRepository.findByUserId(userId);
		}
		return null;
	}

	@Override
	public Boolean markAllNotificationOfUserAsSeen(Long userId) {
		if (userId != null && notificationRepository.existsByUserId(userId)) {
			notificationRepository.markAllNotificationOfUserAsSeen(userId);
			return !notificationRepository.existsByUserIdAndSeenIsFalse(userId);
		}
		return false;
	}

	@Override
	public boolean deleteNotification(Long id) {
		if (id != null && notificationRepository.existsById(id)) {
			notificationRepository.deleteById(id);
			return !notificationRepository.existsById(id);
		}
		return false;
	}

	@Override
	public Notification createNotoificationForAcceptedRequest(ConsultationRequest request) {
		Notification notification = new Notification();
		notification.setContent("Votre demande a été acceptée par un professionnel de santé");
		notification.setUser(request.getPatient());
		notification.setProfessional(request.getProfessional());
		notification.setSeen(false);
		return notificationRepository.save(notification);
	}

}
