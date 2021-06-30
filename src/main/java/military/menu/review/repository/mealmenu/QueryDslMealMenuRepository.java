package military.menu.review.repository.mealmenu;

import military.menu.review.domain.MealMenu;

import java.time.LocalDate;
import java.util.List;

public interface QueryDslMealMenuRepository {
    List<MealMenu> findByDateBetween(LocalDate start, LocalDate end);
}
