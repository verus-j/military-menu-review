package military.menu.review.repository;

import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Week;
import military.menu.review.repository.impl.OrderByLikeMenuRepository;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, OrderByLikeMenuRepository {
    Menu findByName(String name);

    @Query("select distinct mn from Like l join l.menu mn " +
            "join l.member m where m=:member and l.week=:week")
    List<Menu> findByMemberLikedDuringWeek(@Param("member") Member member, @Param("week") Week week);
}
