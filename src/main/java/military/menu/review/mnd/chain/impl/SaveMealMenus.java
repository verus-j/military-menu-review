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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SaveMealMenus extends MndSaveBodyChain {
    private final MealMenuRepository mealMenuRepository;

    @Override
    protected void process(MndSaveChainCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream().forEach(d -> savePerDailyMeal(cache, d));
    }

    private void savePerDailyMeal(MndSaveChainCache cache, DailyMealDTO dto) {
        saveByType(cache, dto.getBreakfast(), dto.getDate(), MealType.BREAKFAST);
        saveByType(cache, dto.getLunch(), dto.getDate(), MealType.LUNCH);
        saveByType(cache, dto.getDinner(), dto.getDate(), MealType.DINNER);
    }

    private void saveByType(MndSaveChainCache cache, MealDTO dto, LocalDate date, MealType type) {
        findMeal(cache, date, type).ifPresent(meal -> saveMealMenu(cache, dto, meal));
    }

    private Optional<Meal> findMeal(MndSaveChainCache cache, LocalDate date, MealType type) {
        return Optional.ofNullable(cache.findEntity(Meal.class, new MealInfo(date, type)));
    }

    private void saveMealMenu(MndSaveChainCache cache, MealDTO dto, Meal meal) {
        AtomicInteger order = new AtomicInteger(0);
        dto.getMenus().stream()
            .map(MenuDTO::getName)
            .map(n -> findMenu(cache, n))
            .map(m -> MealMenu.of(meal, m, order.incrementAndGet()))
            .forEach(mealMenuRepository::save);
    }

    private Menu findMenu(MndSaveChainCache cache, String name) {
        Menu menu = cache.findEntity(Menu.class, name);

        if (menu == null) {
            throw new IllegalStateException();
        }

        return menu;
    }
}
