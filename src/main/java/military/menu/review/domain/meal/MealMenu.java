package military.menu.review.domain;

import lombok.Getter;
import lombok.ToString;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
public class MealMenu{
    private String menuName;
    @Column(name="orders")
    private int order;

    protected MealMenu() {}

    private MealMenu(Meal meal, Menu menu, int order) {
        this.meal = meal;
        this.menu = menu;
        this.order = order;
    }


    public static MealMenu of(Meal meal, Menu menu, int order) {
        return new MealMenu(meal, menu, order);
    }
}
