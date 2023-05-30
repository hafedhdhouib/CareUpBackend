package care.up.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import care.up.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	Page<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(Long senderId, Long recieverId, Long sender,
			Long reciever, Pageable pageable);

	Page<Message> findByDiscussionId(Long discussionId, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("update Message m set m.seen = true where m.discussion.id = ?1")
	public void changeAllMessageOfDiscToSeen(Long discId);

}
