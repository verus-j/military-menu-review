package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Meal;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.repository.MealRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealService {
    private final MealRepository mealRepository;
    private final DailyMealRepository dailyMealRepository;

    public Meal findById(long id) {
        Optional<Meal> optional = mealRepository.findById(id);

        if(optional.isPresent()) {
            Meal meal = optional.get();
            meal.getDailyMeal().getDate();
            return meal;
        }

        throw new IllegalArgumentException();
    }
}
