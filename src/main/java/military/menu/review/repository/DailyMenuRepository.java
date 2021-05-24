package military.menu.review.repository;

import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DailyMenuRepository {
    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<DailyMenu> find(LocalDate date) {
        Optional<Integer> dateId = findDateId(date);

        if(dateId.isPresent()) {
            DailyMenu dailyMenu = new DailyMenu(date);
            findDailyMenuListByType(dateId.get(), DailyMenuType.BREAKFAST).stream().forEach(dailyMenu::addBreakfastMenu);
            findDailyMenuListByType(dateId.get(), DailyMenuType.LUNCH).stream().forEach(dailyMenu::addLunchMenu);
            findDailyMenuListByType(dateId.get(), DailyMenuType.DINNER).stream().forEach(dailyMenu::addDinnerMenu);
            return Optional.of(dailyMenu);
        }

        return Optional.empty();
    }

    private List<Menu> findDailyMenuListByType(int dateId, DailyMenuType type) {
        String sql = String.format(
                "select Menu.name, Menu.calorie, Menu.totalLike " +
                "from Menu inner join %sMenu on Menu.name = %sMenu.name " +
                "where %sMenu.id=?",
                tableName(type), tableName(type), tableName(type)
        );
        List<Map<String, Object>> rows = template.queryForList(sql, dateId);

        return rows.stream().map(
            row -> Menu.of((String)row.get("name"),
                        (Double)row.get("calorie"),
                        (Integer) row.get("totalLike"))
        ).collect(Collectors.toList());
    }

    public void insert(DailyMenu dailyMenu) {
        template.update("insert into Date(date) values(?)", formattedDate(dailyMenu.getDate()));
        int dateId = findDateId(dailyMenu.getDate()).get();

        insertDailyMenuListByType(dateId, dailyMenu, DailyMenuType.BREAKFAST);
        insertDailyMenuListByType(dateId, dailyMenu, DailyMenuType.LUNCH);
        insertDailyMenuListByType(dateId, dailyMenu, DailyMenuType.DINNER);
    }

    private void insertDailyMenuListByType(int dateId, DailyMenu dailyMenu, DailyMenuType type) {
        String sql = String.format("insert into %s(id, totalCalorie) values(?, ?)", tableName(type));
        template.update(sql, dateId, totalCalorie(dailyMenu, type));

        List<Menu> list = menuList(dailyMenu, type).getList();
        sql = String.format("insert into %sMenu(id, name, menuOrder) values(?, ?, ?)", tableName(type));

        for(int index = 0; index < list.size(); index++) {
            Menu menu = list.get(index);
            template.update(sql, dateId, menu.getName(), index);
        }
    }

    private Optional<Integer> findDateId(LocalDate date) {
        try {
            int id = template.queryForObject("select id from Date where date=?", Integer.class, formattedDate(date));
            return Optional.of(id);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private String formattedDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private double totalCalorie(DailyMenu menu, DailyMenuType type) {
        return menuList(menu, type).getTotalCalorie();
    }

    private MenuList menuList(DailyMenu menu, DailyMenuType type) {
        switch(type) {
            case BREAKFAST:
                return menu.getBreakfast();
            case LUNCH:
                return menu.getLunch();
            case DINNER:
                return menu.getDinner();
            default:
                throw new IllegalArgumentException();
        }
    }

    private String tableName(DailyMenuType type) {
        switch(type) {
            case BREAKFAST:
                return "Breakfast";
            case LUNCH:
                return "Lunch";
            case DINNER:
                return "Dinner";
            default:
                throw new IllegalArgumentException();
        }
    }
}
