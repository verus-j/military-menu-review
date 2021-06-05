package military.menu.review.mnd.chain.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.dto.MealDTO;
import military.menu.review.domain.dto.MenuDTO;
import military.menu.review.domain.entity.Meal;
import military.menu.review.domain.entity.MealMenu;
import military.menu.review.domain.entity.MealType;
import military.menu.review.domain.entity.Menu;
import military.menu.review.mnd.chain.MealInfo;
import military.menu.review.mnd.chain.MndSaveChainCache;
import military.menu.review.repository.MealMenuRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.repository.MenuRepository;
import military.menu.review.mnd.chain.MndSaveBodyChain;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class SaveMealMenus extends MndSaveBodyChain {
    private final EntityManager em;
    private final MealMenuRepository mealMenuRepository;

    @Override
    protected void process(MndSaveChainCache cache) {
        cache.getDailyMeals().stream().forEach(d -> savePerDailyMeal(cache, d));
    }

    private void savePerDailyMeal(MndSaveChainCache cache, DailyMealDTO dto) {
        saveByType(cache, dto.getBreakfast(), dto.getDate(), MealType.BREAKFAST);
        saveByType(cache, dto.getLunch(), dto.getDate(), MealType.LUNCH);
        saveByType(cache, dto.getDinner(), dto.getDate(), MealType.DINNER);
    }

    private void saveByType(MndSaveChainCache cache, MealDTO dto, LocalDate date, MealType type) {
        final AtomicInteger order = new AtomicInteger(0);
        Meal meal = findMeal(cache, date, type);

        dto.getMenus().stream()
                .map(MenuDTO::getName)
                .map(n -> findMenu(cache, n))
                .map(menu -> MealMenu.of(meal, menu, order.incrementAndGet()))
                .forEach(mealMenuRepository::save);
    }

    private Meal findMeal(MndSaveChainCache cache, LocalDate date, MealType type) {
        Meal meal = em.find(Meal.class, cache.findMealId(new MealInfo(date, type)));

        if(meal == null) {
            throw new IllegalStateException();
        }

        return meal;
    }

    private Menu findMenu(MndSaveChainCache cache, String name) {
        Menu menu = em.find(Menu.class, cache.findMenuId(name));

        if (menu == null) {
            throw new IllegalStateException();
        }

        return menu;
    }
}
