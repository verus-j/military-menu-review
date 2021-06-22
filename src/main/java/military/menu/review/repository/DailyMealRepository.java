package military.menu.review.repository;

import military.menu.review.domain.DailyMeal;
import military.menu.review.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMeal, Long> {
    DailyMeal findByDate(LocalDate date);
    List<DailyMeal> findByDateBetween(LocalDate start ,LocalDate end);
}
