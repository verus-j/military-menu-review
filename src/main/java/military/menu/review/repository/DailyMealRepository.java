package military.menu.review.repository;

import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.entity.DailyMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMeal, Long> {
    DailyMeal findByDate(LocalDate date);
    List<DailyMeal> findByDateBetween(LocalDate start, LocalDate end);
}
