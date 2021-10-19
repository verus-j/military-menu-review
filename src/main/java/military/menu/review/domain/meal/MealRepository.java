package military.menu.review.domain.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findByDateAndMealType(LocalDate date, MealType mealType);
}
