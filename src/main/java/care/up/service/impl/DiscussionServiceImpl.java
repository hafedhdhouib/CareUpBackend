package care.up.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import care.up.model.Discussion;
import care.up.model.Message;
import care.up.model.User;
import care.up.repository.DiscussionRepository;
import care.up.repository.UserRepository;
import care.up.service.DiscussionService;
import care.up.service.MessageService;

@Service
public class DiscussionServiceImpl implements DiscussionService {

	@Autowired
	DiscussionRepository discussionRepository;

	@Autowired
	UserRepository userRepository;

	private final MessageService messageService;

	public DiscussionServiceImpl(@Lazy MessageService service) {
		this.messageService = service;
	}

	@Override
	public List<Discussion> getAllByUserId(Long userId, Pageable pageable) {
		if (userId != null) {
			List<Discussion> res = discussionRepository.findByFirstUserIdOrSecondUserId(userId, userId, pageable)
					.toList();
			res.stream().map(d -> {
				List<Message> list = new ArrayList<>(messageService.getMessagesOfDiscussion(d.getId(),
						PageRequest.of(0, 20, Sort.by("created").descending())));
				d.setMessages(revertList(list));
				return d;
			}).collect(Collectors.toList());
			return res;
		}
		return null;
	}

	@Override
	public Discussion addNewDiscussion(Discussion discussion) {
		if (discussion != null
				&& !existsDiscussionBetween(discussion.getFirstUser().getId(), discussion.getSecondUser().getId())) {
			Optional<User> firstUser = userRepository.findById(discussion.getFirstUser().getId());
			Optional<User> secondUser = userRepository.findById(discussion.getSecondUser().getId());
			if (firstUser.isPresent() && secondUser.isPresent()) {
				discussion.setFirstUser(firstUser.get());
				discussion.setSecondUser(secondUser.get());
				Discussion res = discussionRepository.save(discussion);
				List<Message> list = new ArrayList<>(messageService.getMessagesOfDiscussion(res.getId(),
						PageRequest.of(0, 20, Sort.by("created").descending())));
				res.setMessages(revertList(list));
				return res;
			}
		}
		return null;
	}

	@Override
	public boolean deleteDiscussionById(Long id) {
		if (id != null && discussionRepository.existsById(id)) {
			discussionRepository.deleteById(id);
			return !discussionRepository.existsById(id);
		}
		return false;
	}

	@Override
	public boolean existsDiscussionBetween(Long firstUserId, Long secondUserId) {
		return discussionRepository.existsByFirstUserIdAndSecondUserIdOrFirstUserIdAndSecondUserId(firstUserId,
				secondUserId, secondUserId, firstUserId);
	}

	@Override
	public Discussion getDiscussionBetween(Long firstUserId, Long secondUserId) {
		if (firstUserId != null && secondUserId != null) {
			Discussion res = discussionRepository.findByFirstUserIdAndSecondUserIdOrFirstUserIdAndSecondUserId(
					firstUserId, secondUserId, secondUserId, firstUserId);

			List<Message> list = new ArrayList<>(messageService.getMessagesOfDiscussion(res.getId(),
					PageRequest.of(0, 20, Sort.by("created").descending())));
			res.setMessages(revertList(list));
			return res;
		}
		return null;
	}

	@Override
	public Discussion getDiscussionById(Long discussionId) {
		Optional<Discussion> optional = discussionRepository.findById(discussionId);
		if (optional.isPresent()) {
			Discussion res = optional.get();
			List<Message> list = new ArrayList<>(messageService.getMessagesOfDiscussion(res.getId(),
					PageRequest.of(0, 20, Sort.by("created").descending())));
			res.setMessages(revertList(list));
			return res;
		}
		return null;
	}

	private List<Message> revertList(List<Message> list) {
		if (list != null && !list.isEmpty()) {
			Collections.reverse(list);
		}
		return list;
	}
}
