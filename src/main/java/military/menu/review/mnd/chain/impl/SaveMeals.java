package military.menu.review.mnd.chain.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.dto.MenuDTO;
import military.menu.review.domain.entity.DailyMeal;
import military.menu.review.domain.entity.Meal;
import military.menu.review.domain.entity.MealType;
import military.menu.review.mnd.chain.MealInfo;
import military.menu.review.mnd.chain.MndSaveChainCache;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.mnd.chain.MndSaveBodyChain;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@RequiredArgsConstructor
public class SaveMeals extends MndSaveBodyChain {
    private final EntityManager em;
    private final MealRepository mealRepository;

    @Override
    protected void process(MndSaveChainCache cache) {
        cache.getDailyMeals().stream().map(DailyMealDTO::getDate).forEach(d -> savePerDate(cache, d));
    }

    private void savePerDate(MndSaveChainCache cache, LocalDate date) {
        saveByMealType(cache, date, MealType.BREAKFAST);
        saveByMealType(cache, date, MealType.LUNCH);
        saveByMealType(cache, date, MealType.DINNER);
    }

    private void saveByMealType(MndSaveChainCache cache, LocalDate date, MealType type) {
        Meal meal = mealRepository.save(Meal.of(type, findDailyMeal(cache, date)));
        cache.saveMealId(new MealInfo(date, type), meal.getId());
    }

    private DailyMeal findDailyMeal(MndSaveChainCache cache, LocalDate date) {
        DailyMeal dailyMeal = em.find(DailyMeal.class, cache.findDailyMealId(date));

        if(dailyMeal == null){
            throw new IllegalStateException();
        }

        return dailyMeal;
    }


}
