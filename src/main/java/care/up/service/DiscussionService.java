package care.up.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import care.up.model.Discussion;

public interface DiscussionService {

	public List<Discussion> getAllByUserId(Long userId, Pageable pageable);

	public Discussion addNewDiscussion(Discussion discussion);

	public Discussion getDiscussionById(Long discussionId);

	public boolean deleteDiscussionById(Long id);

	public boolean existsDiscussionBetween(Long firstUserId, Long secondUserId);

	public Discussion getDiscussionBetween(Long firstUserId, Long secondUserId);
}
