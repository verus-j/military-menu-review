package military.menu.review.repository;

import military.menu.review.domain.MealMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealMenuRepository extends JpaRepository<MealMenu, Long> {
    @Query("select mm from MealMenu mm join fetch mm.meal join fetch mm.menu join mm.meal ma where ma.date between :start and :end")
    List<MealMenu> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
