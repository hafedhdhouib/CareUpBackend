package care.up.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import care.up.config.webSocket.WSService;
import care.up.model.Discussion;
import care.up.model.Message;
import care.up.model.User;
import care.up.repository.DiscussionRepository;
import care.up.repository.MessageRepository;
import care.up.repository.UserRepository;
import care.up.service.DiscussionService;
import care.up.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DiscussionRepository discussionRepository;

	@Autowired
	DiscussionService discussionService;

	@Autowired
	private WSService wsSservice;

	@Override
	public Message sendMessage(Message message) {
		if (message != null) {
			Optional<User> sender = userRepository.findById(message.getSender().getId());
			Optional<User> receiver = userRepository.findById(message.getReceiver().getId());
			if (sender.isPresent() && receiver.isPresent()) {
				message.setSender(sender.get());
				message.setReceiver(receiver.get());
				if (discussionService.existsDiscussionBetween(message.getSender().getId(),
						message.getReceiver().getId())) {
					message.setDiscussion(discussionService.getDiscussionBetween(message.getSender().getId(),
							message.getReceiver().getId()));
				} else {
					Discussion discussion = new Discussion();
					discussion.setFirstUser(sender.get());
					discussion.setSecondUser(receiver.get());
					message.setDiscussion(discussionRepository.save(discussion));
				}
				message.setSeen(false);
				Message res = messageRepository.save(message);
				wsSservice.sendDiscussionToUser(message.getReceiver().getPhoneNumber(),
						discussionService.getDiscussionById(message.getDiscussion().getId()));
				return res;
			}
		}
		return null;
	}

	@Override
	public List<Message> getMessagesBetween(Long user1Id, Long user2Id, Pageable pageable) {

		if (user1Id != null && user2Id != null) {
			return messageRepository
					.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(user1Id, user2Id, user2Id, user1Id, pageable)
					.toList();
		}
		return null;
	}

	@Override
	public List<Message> getMessagesOfDiscussion(Long discId, Pageable pageable) {
		return messageRepository.findByDiscussionId(discId, pageable).toList();
	}

	@Override
	public void changeAllMessageOfDiscToSeen(Long discId) {
		messageRepository.changeAllMessageOfDiscToSeen(discId);

	}

}
