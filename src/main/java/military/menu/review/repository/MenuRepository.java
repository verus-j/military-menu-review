package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByName(String name);
}
