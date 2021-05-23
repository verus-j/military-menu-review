package military.menu.review.model.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Menu {
    public final String name;
    public final double calorie;

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
        return new Menu(name, calorie);
    }
}
