package military.menu.review.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Menu {
    @Id @GeneratedValue @Column(name="menu_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private Double kcal;
    @Column(name="likes")
    private Integer like;

    @OneToMany(mappedBy="menu")
    private List<MealMenu> mealMenus = new ArrayList<>();

    protected Menu() {}

    private Menu(String name, Double kcal) {
        this.name = name;
        this.kcal = kcal;
        this.like = 0;
    }

    public void like() {
        like++;
    }

    public void unlike() {
        like--;
    }

    public static Menu of(String name, Double kcal) {
        return new Menu(name, kcal);
    }
}
