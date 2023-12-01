package care.up.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import care.up.model.Discussion;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

	public Page<Discussion> findByFirstUserIdOrSecondUserId(Long firstUserId, Long secondUserId, Pageable pageable);

	public boolean existsByFirstUserIdAndSecondUserIdOrFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId,
			Long user1Id, Long user2Id);
	
	public Discussion findByFirstUserIdAndSecondUserIdOrFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId,
			Long user1Id, Long user2Id);
}
