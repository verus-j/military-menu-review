package military.menu.review.domain.meal;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Embeddable
public class MealMenu{
    @Column(name="orders")
    private int order;
    private String menuName;

    protected MealMenu() {}

    private MealMenu(String menuName, int order) {
        this.menuName = menuName;
        this.order = order;
    }


    public static MealMenu of(String menuName, int order) {
        return new MealMenu(menuName, order);
    }
}
