package military.menu.review.model.menu;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Menu {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private double kcal;

    public Menu() { }

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

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public static Menu of(String name, double kcal) {
        return new Menu(name, kcal);
    }
}
