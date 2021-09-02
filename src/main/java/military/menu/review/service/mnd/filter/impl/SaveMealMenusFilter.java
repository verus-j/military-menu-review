package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.MealType;
import military.menu.review.domain.menu.Menu;
import military.menu.review.service.mnd.filter.MealInfo;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.repository.mealmenu.MealMenuRepository;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;
import military.menu.review.service.mnd.filter.exception.MenuNotFoundException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class SaveMealMenusFilter extends MndSaveProcessFilter {
    private final MealMenuRepository mealMenuRepository;

    @Override
    protected void process(MndFilterCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream().forEach(d -> savePerDailyMeal(cache, d));
    }

    private void savePerDailyMeal(MndFilterCache cache, DailyMealDTO dto) {
        saveByType(cache, dto.getBreakfast(), dto.getDate(), MealType.BREAKFAST);
        saveByType(cache, dto.getLunch(), dto.getDate(), MealType.LUNCH);
        saveByType(cache, dto.getDinner(), dto.getDate(), MealType.DINNER);
    }

    private void saveByType(MndFilterCache cache, MealDTO dto, LocalDate date, MealType type) {
        findMeal(cache, date, type).ifPresent(meal -> saveMealMenu(cache, dto, meal));
    }

    private Optional<Meal> findMeal(MndFilterCache cache, LocalDate date, MealType type) {
        return Optional.ofNullable(cache.findEntity(Meal.class, new MealInfo(date, type)));
    }

    private void saveMealMenu(MndFilterCache cache, MealDTO dto, Meal meal) {
        AtomicInteger order = new AtomicInteger(0);
        dto.getMenus().stream()
            .map(MenuDTO::getName)
            .map(n -> findMenu(cache, n))
            .map(m -> MealMenu.of(meal, m, order.incrementAndGet()))
            .forEach(mealMenuRepository::save);
    }

    private Menu findMenu(MndFilterCache cache, String name) {
        Menu menu = cache.findEntity(Menu.class, name);

        if (menu == null) {
            throw new MenuNotFoundException();
        }

        return menu;
    }
}
