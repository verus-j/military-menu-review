package military.menu.review.model.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Menu {
    private String name;
    private double calorie;
    private int totalLike;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o.getClass() == getClass())) return false;
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", calorie=" + calorie +
                '}';
    }

    public static Menu of(String name, double calorie) {
        return new Menu(name, calorie, 0);
    }

    public static Menu of(String name, double calorie, int totalLike) {
        return new Menu(name, calorie, totalLike);
    }
}
