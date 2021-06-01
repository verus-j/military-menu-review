package military.menu.review.model.menu;

import java.util.Objects;

public class Menu {
    private String name;
    private double kcal;

    public Menu(String name, double kcal) {
        this.name = name;
        this.kcal = kcal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return name.equals(menu.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public double getKcal() {
        return kcal;
    }

    public static Menu of(String name, double kcal) {
        return new Menu(name, kcal);
    }
}
