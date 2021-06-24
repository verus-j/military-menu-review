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
    @Query("select m from Meal m where m.id=:id")
    Optional<Meal> findById(@Param("id") Long id);

    @Query("select m from Meal m where m.type=:type and m.date=:date")
    Meal findByDateAndType(@Param("date") LocalDate date, @Param("type") MealType type);
}
