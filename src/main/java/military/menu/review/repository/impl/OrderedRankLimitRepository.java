package military.menu.review.repository.impl;

import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;

import java.util.List;

public interface OrderedRankLimitRepository {
    List<Rank> findByWeekOrderByRankLimit(Week week, int limit);
}
