package military.menu.review.model;

import lombok.Data;

@Data
public class Menu {
    public final String name;
    public final double calorie;

    public static Menu of(String name, double calorie) {
        return new Menu(name, calorie);
    }
}
