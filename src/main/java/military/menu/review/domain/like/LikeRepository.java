package military.menu.review.domain.like;

import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByMemberAndMenu(Member member, Menu menu);
    Page<Like> findAllByMenu(Menu menu, Pageable pageable);
    void deleteByMemberAndMenu(Member member, Menu menu);
}
