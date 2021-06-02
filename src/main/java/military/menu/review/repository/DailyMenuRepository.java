package military.menu.review.repository;

import military.menu.review.model.menu.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyMenuRepository extends JpaRepository<DailyMenu, Long> {
    DailyMenu findByDate(LocalDate date);
    List<DailyMenu> findByDateBetweenOrderByDateAsc(LocalDate from, LocalDate to);
}
