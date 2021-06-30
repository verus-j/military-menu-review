package military.menu.review.repository.mealmenu;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.QMeal;
import military.menu.review.domain.QMealMenu;
import military.menu.review.domain.QMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static military.menu.review.domain.QMeal.meal;
import static military.menu.review.domain.QMealMenu.mealMenu;
import static military.menu.review.domain.QMenu.menu;

@Repository
@RequiredArgsConstructor
public class QueryDslMealMenuRepositoryImpl implements QueryDslMealMenuRepository{
    private final EntityManager em;

    @Override
    public List<MealMenu> findByDateBetween(LocalDate start, LocalDate end) {
        JPAQuery query = new JPAQuery(em);

        return query.from(mealMenu)
            .join(mealMenu.meal, meal).fetch()
            .join(mealMenu.menu, menu).fetch()
            .where(meal.date.between(start, end))
            .list(mealMenu);
    }
}
