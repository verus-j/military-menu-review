package military.menu.review.domain.dto;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.entity.DailyMeal;
import military.menu.review.domain.entity.Meal;
import military.menu.review.domain.entity.MealType;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class DailyMealDTO {
    private LocalDate date;
    private MealDTO breakfast;
    private MealDTO lunch;
    private MealDTO dinner;

    public DailyMealDTO(DailyMeal dailyMeal) {
        this.date = dailyMeal.getDate();
        this.breakfast = toMealDTO(dailyMeal, MealType.BREAKFAST);
        this.lunch = toMealDTO(dailyMeal, MealType.LUNCH);
        this.dinner = toMealDTO(dailyMeal, MealType.DINNER);
    }

    private DailyMealDTO(LocalDate date) {
        this.date = date;
        this.breakfast = MealDTO.empty();
        this.lunch = MealDTO.empty();
        this.dinner = MealDTO.empty();
    }

    private MealDTO toMealDTO(DailyMeal dailyMeal, MealType type) {
        Meal meal = dailyMeal.getMeals().stream()
                .filter(m -> m.getType().equals(type)).findFirst().orElse(null);

        return meal == null ? MealDTO.empty() : new MealDTO(meal);
    }

    public void addBreakfastMenu(MenuDTO menu) {
        breakfast.addMenu(menu);
    }

    public void addLunchMenu(MenuDTO menu) {
        lunch.addMenu(menu);
    }

    public void addDinnerMenu(MenuDTO menu) {
        dinner.addMenu(menu);
    }

    public static DailyMealDTO of(LocalDate date) {
        return new DailyMealDTO(date);
    }
}
