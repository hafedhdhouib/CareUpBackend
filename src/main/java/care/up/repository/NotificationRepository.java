package care.up.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import care.up.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	public List<Notification> findByUserId(Long userId);

	@Modifying
	@Transactional
	@Query("update Notification n set n.seen = true where n.user.id = ?1")
	public void markAllNotificationOfUserAsSeen(Long userId);

	public Boolean existsByUserIdAndSeenIsFalse(Long userId);
	
	public Boolean existsByUserId(Long userId);
}
