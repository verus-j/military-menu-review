package military.menu.review.repository.rank;

import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;

import java.util.List;

public interface QueryDslRankRepository {
    List<Rank> findByWeekOrderByRankLimit(Week week, int limit);
}
