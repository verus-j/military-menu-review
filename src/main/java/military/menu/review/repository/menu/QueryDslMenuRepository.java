package military.menu.review.repository.menu;

import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.Week;

import java.util.List;

public interface QueryDslMenuRepository {
    List<Menu> findByMemberLikedDuringWeek(Member member, Week week);
    List<Menu> findOrderByLikeLimit(int limit);
}
