package military.menu.review.repository.like;

import military.menu.review.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>, QueryDslLikeRepository {
}
