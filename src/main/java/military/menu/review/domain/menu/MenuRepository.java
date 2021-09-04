package military.menu.review.repository.menu;

import military.menu.review.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, QueryDslMenuRepository {
    Menu findByName(String name);
}
