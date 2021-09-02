package military.menu.review.repository.like;

import military.menu.review.domain.Like;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.Week;

public interface QueryDslLikeRepository {
    Like findByMemberAndMenuAndWeek(Member member, Menu menu, Week week);
}
