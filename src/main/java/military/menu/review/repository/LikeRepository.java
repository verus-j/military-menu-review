package military.menu.review.repository;

import military.menu.review.domain.Like;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l join l.member m join l.menu mn join fetch l.menu where m=:member and mn=:menu and l.week=:week")
    Like findByMemberAndMenuAndWeek(@Param("member") Member member, @Param("menu") Menu menu, @Param("week") Week week);
}
