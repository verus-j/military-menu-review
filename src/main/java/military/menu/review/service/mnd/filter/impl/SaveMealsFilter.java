package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.MealType;
import military.menu.review.service.dto.MealDTO;
import military.menu.review.service.mnd.filter.MealInfo;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.repository.MealRepository;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SaveMealsFilter extends MndSaveProcessFilter {
    private final MealRepository mealRepository;

    @Override
    protected void process(MndFilterCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream()
            .forEach(d -> savePerDate(cache, d));
    }

    private void savePerDate(MndFilterCache cache, DailyMealDTO dto) {
        if(isNotEmptyMeal(dto.getBreakfast())) {
            saveByMealType(cache, dto.getDate(), MealType.BREAKFAST);
        }

        if(isNotEmptyMeal(dto.getLunch())) {
            saveByMealType(cache, dto.getDate(), MealType.LUNCH);
        }

        if(isNotEmptyMeal(dto.getDinner())) {
            saveByMealType(cache, dto.getDate(), MealType.DINNER);
        }
    }

    private boolean isNotEmptyMeal(MealDTO mealDTO) {
        return mealDTO.getMenus().size() != 0;
    }

    private void saveByMealType(MndFilterCache cache, LocalDate date, MealType type) {
        Meal meal = findMeal(date, type);

        if(notInDB(meal)) {
            meal = mealRepository.save(Meal.of(type,date));
            cache.putEntity(new MealInfo(date, type), meal);
        }
    }

    private Meal findMeal(LocalDate date, MealType type) {
        return mealRepository.findByDateAndType(date, type);
    }

    private boolean notInDB(Meal meal) {
        return meal == null;
    }
}
