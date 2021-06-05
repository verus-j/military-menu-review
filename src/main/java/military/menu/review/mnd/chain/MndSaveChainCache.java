package military.menu.review.mnd.chain;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.dto.MenuDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MndSaveChainCache {
    private final List<MenuDTO> menus;
    private final List<DailyMealDTO> dailyMeals;
    private final Map<String, Long> menuIds;
    private final Map<LocalDate, Long> dailyMealIds;
    private final Map<MealInfo, Long> mealIds;

    public MndSaveChainCache(List<MenuDTO> menus, List<DailyMealDTO> dailyMeals) {
        this.menus = menus;
        this.dailyMeals = dailyMeals;
        this.menuIds = new HashMap<>();
        this.dailyMealIds = new HashMap<>();
        this.mealIds = new HashMap<>();
    }

    public List<DailyMealDTO> getDailyMeals() {
        return dailyMeals;
    }

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public Long findMenuId(String name) {
        return menuIds.get(name);
    }

    public Long findDailyMealId(LocalDate date) {
        return dailyMealIds.get(date);
    }

    public Long findMealId(MealInfo info) {
        return mealIds.get(info);
    }

    public void saveMenuId(String name, Long id) {
        menuIds.put(name, id);
    }

    public void saveDailyMealId(LocalDate date, Long id) {
        dailyMealIds.put(date, id);
    }

    public void saveMealId(MealInfo info, Long id) {
        mealIds.put(info, id);
    }
}
