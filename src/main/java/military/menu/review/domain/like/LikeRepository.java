package military.menu.review.domain.like;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByMemberIdAndMenuId(Long memberId, Long menuId);
    Page<Like> findAllByMenuId(Long menuId, Pageable pageable);
    void deleteByMemberIdAndMenuId(Long memberId, Long menuId);
}
