package military.menu.review.domain.menu;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="menu_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private Double kcal;
    @Column(name="likes")
    private Long like;

    protected Menu() {}

    private Menu(String name, Double kcal) {
        this.name = name;
        this.kcal = kcal;
        this.like = 0L;
    }

    public void like() {
        like++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static Menu of(String name, Double kcal) {
        return new Menu(name, kcal);
    }

    public void unlike() {
        like--;
    }
}
