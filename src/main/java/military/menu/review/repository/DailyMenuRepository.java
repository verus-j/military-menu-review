package military.menu.review.repository;

import military.menu.review.model.menu.DailyMenu;

import java.util.List;

public interface DailyMenuRepository {
    List<DailyMenu> findAll();
    void insert();
    void insertAll(List<DailyMenu> list);
}
