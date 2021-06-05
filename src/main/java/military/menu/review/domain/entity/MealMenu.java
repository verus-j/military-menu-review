package military.menu.review.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@IdClass(MealMenuId.class)
@Getter
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
    public int compareTo(MealMenu mealMenu) {
        return Integer.compare(order, mealMenu.order);
    }

    public static MealMenu of(Meal meal, Menu menu, int order) {
        return new MealMenu(meal, menu, order);
    }
}
