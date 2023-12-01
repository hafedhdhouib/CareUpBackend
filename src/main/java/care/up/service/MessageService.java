package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.Message;

public interface MessageService {

	public Message sendMessage(Message message);

	public List<Message> getMessagesBetween(Long user1Id, Long user2Id, Pageable pageable);
	
	public List<Message> getMessagesOfDiscussion(Long discId, Pageable pageable);
	
	public void changeAllMessageOfDiscToSeen(Long discId);
}
