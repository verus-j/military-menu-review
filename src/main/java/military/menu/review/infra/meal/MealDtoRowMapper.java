package military.menu.review.infra.meal;

import military.menu.review.domain.meal.MealDto;
import military.menu.review.domain.meal.MealType;
import military.menu.review.domain.menu.MenuDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MealDtoRowMapper implements RowMapper<MealDto> {
    private boolean isLogin;
    private MealDto meal;

    public MealDtoRowMapper(boolean isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public MealDto mapRow(ResultSet rs, int i) throws SQLException {
        if(isCurrentMealEmpty()) {
            initCurrentMeal(rs);
        }

        return isNextMeal(rs) ? getCurrentMealAndInitNextMeal(rs) : getCurrentMeal(rs);
    }

    private boolean isCurrentMealEmpty() {
        return meal == null;
    }

    private void initCurrentMeal(ResultSet rs) throws SQLException {
        meal = meal(rs);
    }

    private boolean isNextMeal(ResultSet rs) throws SQLException {
        return !meal.getId().equals(rs.getLong("meal_id"));
    }

    private MealDto getCurrentMealAndInitNextMeal(ResultSet rs) throws SQLException {
        MealDto result = meal;
        meal = meal(rs);
        meal.addMenu(menu(isLogin, rs));
        return result;
    }

    private MealDto getCurrentMeal(ResultSet rs) throws SQLException {
        meal.addMenu(menu(isLogin, rs));

        while(rs.next()) {
            if(isNextMeal(rs)) {
                return getCurrentMealAndInitNextMeal(rs);
            }

            meal.addMenu(menu(isLogin, rs));
        }

        return meal;
    }

    private MealDto meal(ResultSet rs) throws SQLException {
        return MealDto.builder()
                .id(rs.getLong("meal_id"))
                .date(toLocalDate(rs.getDate("date")))
                .mealType(MealType.valueOf(rs.getString("meal_type")))
                .menus(new ArrayList<>())
                .build();
    }

    private MenuDto menu(boolean isLogin, ResultSet rs) throws SQLException {
        MenuDto menu = MenuDto.builder()
                .isLogin(isLogin)
                .id(rs.getLong("menu_id"))
                .name(rs.getString("name"))
                .kcal(rs.getDouble("kcal"))
                .like(rs.getLong("likes"))
                .isLiked(rs.getBoolean("is_liked"))
                .build();
        return menu;
    }

    private LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }
}
