package military.menu.review.repository;

import military.menu.review.model.menu.Menu;

import java.util.List;

public interface MenuRepository {
    List<Menu> findAll();
    Menu findByName(String name);
    void insert(Menu menu);
    void insertAll(List<Menu> list);
}
