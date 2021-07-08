package military.menu.review.repository;

import military.menu.review.domain.Meal;
import military.menu.review.domain.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findById(Long id);
    Meal findByDateAndType(LocalDate date, MealType type);
}
