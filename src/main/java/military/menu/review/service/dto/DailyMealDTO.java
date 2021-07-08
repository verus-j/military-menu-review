package military.menu.review.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.MealType;
import java.time.LocalDate;

@Getter @Setter @ToString @EqualsAndHashCode
public class DailyMealDTO {
    private LocalDate date;
    private MealDTO breakfast;
    private MealDTO lunch;
    private MealDTO dinner;

    private DailyMealDTO(LocalDate date) {
        this.date = date;
        this.breakfast = MealDTO.empty();
        this.lunch = MealDTO.empty();
        this.dinner = MealDTO.empty();
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
