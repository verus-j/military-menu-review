package military.menu.review.repository;

import military.menu.review.domain.entity.Meal;
import military.menu.review.domain.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    @Query("select m from Meal m join m.dailyMeal d where m.type=:type and d.date=:date")
    Meal findByDateAndType(@Param("date") LocalDate date, @Param("type") MealType type);
}
