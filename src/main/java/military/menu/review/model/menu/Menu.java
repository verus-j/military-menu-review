package military.menu.review.model.menu;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Menu {
    @Id @GeneratedValue @Column(name="MENU_ID")
    private Long id;

    @Column(unique = true)
    private String name;

    private double kcal;

    public Menu() { }

    public Menu(String name, double kcal) {
        this.name = name;
        this.kcal = kcal;
    }

    public Long getId() {
        return id;
    }

    public double getKcal() {
        return kcal;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return name.equals(menu.name) && Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public static Menu of(String name, double kcal) {
        return new Menu(name, kcal);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kcal=" + kcal +
                '}';
    }
}
