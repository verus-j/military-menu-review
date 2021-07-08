package military.menu.review.repository.menu;

import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Week;

import java.util.List;

public interface QueryDslMenuRepository {
    List<Menu> findByMemberLikedDuringWeek(Member member, Week week);
    List<Menu> findOrderByLikeLimit(int limit);
}
