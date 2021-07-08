package military.menu.review.repository.mealmenu;

import military.menu.review.domain.MealMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealMenuRepository extends JpaRepository<MealMenu, Long>, QueryDslMealMenuRepository{
}
