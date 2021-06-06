package military.menu.review.mnd.chain.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.entity.DailyMeal;
import military.menu.review.domain.entity.Meal;
import military.menu.review.domain.entity.MealType;
import military.menu.review.mnd.chain.MealInfo;
import military.menu.review.mnd.chain.MndSaveChainCache;
import military.menu.review.repository.MealRepository;
import military.menu.review.mnd.chain.MndSaveBodyChain;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SaveMeals extends MndSaveBodyChain {
    private final MealRepository mealRepository;

    @Override
    protected void process(MndSaveChainCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream()
            .map(DailyMealDTO::getDate)
            .forEach(d -> savePerDate(cache, d));
    }

    private void savePerDate(MndSaveChainCache cache, LocalDate date) {
        saveByMealType(cache, date, MealType.BREAKFAST);
        saveByMealType(cache, date, MealType.LUNCH);
        saveByMealType(cache, date, MealType.DINNER);
    }

    private void saveByMealType(MndSaveChainCache cache, LocalDate date, MealType type) {
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

    private DailyMeal findDailyMeal(MndSaveChainCache cache, LocalDate date) {
        DailyMeal dailyMeal = cache.findEntity(DailyMeal.class, date);

        if(dailyMeal == null) {
            throw new IllegalStateException();
        }

        return dailyMeal;
    }
}
