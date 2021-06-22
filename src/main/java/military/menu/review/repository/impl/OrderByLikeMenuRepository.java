package military.menu.review.repository.impl;

import military.menu.review.domain.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderByLikeMenuRepository {
    List<Menu> findByWeekOrderByLikeLimit(int limit);
}
