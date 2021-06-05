package military.menu.review.repository;

import military.menu.review.domain.entity.MealMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealMenuRepository extends JpaRepository<MealMenu, Long> {
}
