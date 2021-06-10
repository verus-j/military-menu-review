package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.domain.DailyMeal;
import military.menu.review.domain.Meal;
import military.menu.review.domain.MealType;
import military.menu.review.service.mnd.filter.MealInfo;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.repository.MealRepository;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SaveMeals extends MndSaveProcessFilter {
    private final MealRepository mealRepository;

    @Override
    protected void process(MndFilterCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream()
            .map(DailyMealDTO::getDate)
            .forEach(d -> savePerDate(cache, d));
    }

    private void savePerDate(MndFilterCache cache, LocalDate date) {
        saveByMealType(cache, date, MealType.BREAKFAST);
        saveByMealType(cache, date, MealType.LUNCH);
        saveByMealType(cache, date, MealType.DINNER);
    }

    private void saveByMealType(MndFilterCache cache, LocalDate date, MealType type) {
        Meal meal = findMeal(date, type);

        if(notInDB(meal)) {
            meal = mealRepository.save(Meal.of(type, findDailyMeal(cache, date)));
            cache.putEntity(new MealInfo(date, type), meal);
        }
    }

    private Meal findMeal(LocalDate date, MealType type) {
        return mealRepository.findByDateAndType(date, type);
    }

    private boolean notInDB(Meal meal) {
        return meal == null;
    }

    private DailyMeal findDailyMeal(MndFilterCache cache, LocalDate date) {
        DailyMeal dailyMeal = cache.findEntity(DailyMeal.class, date);

        if(dailyMeal == null) {
            throw new IllegalStateException();
        }

        return dailyMeal;
    }
}
