package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.meal.Meal;
import military.menu.review.repository.MealRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealService {
    private final MealRepository mealRepository;

    public Meal findById(long id) {
        Optional<Meal> optional = mealRepository.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new IllegalArgumentException();
    }
}
