package military.menu.review.domain;

import lombok.Getter;
import lombok.ToString;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.util.Objects;

@IdClass(MealMenuId.class)
@Getter
@ToString
public class MealMenu implements Comparable<MealMenu>{
    @Id @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="meal_id")
    private Meal meal;
    @Id @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_id")
    private Menu menu;
    @Column(name="orders")
    private int order;

    protected MealMenu() {}

    private MealMenu(Meal meal, Menu menu, int order) {
        this.meal = meal;
        this.menu = menu;
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealMenu mealMenu = (MealMenu) o;
        return order == mealMenu.order && Objects.equals(meal, mealMenu.meal) && Objects.equals(menu, mealMenu.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meal, menu, order);
    }

    @Override
    public int compareTo(MealMenu mealMenu) {
        return Integer.compare(order, mealMenu.order);
    }

    public static MealMenu of(Meal meal, Menu menu, int order) {
        return new MealMenu(meal, menu, order);
    }
}
