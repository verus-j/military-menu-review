package military.menu.review.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="menu_id")
    private Long id;
    @Column(unique = true)
    private String name;
    private Double kcal;
    @Column(name="likes")
    private Integer like;

    protected Menu() {}

    private Menu(String name, Double kcal) {
        this.name = name;
        this.kcal = kcal;
        this.like = 0;
    }

    public static Menu of(String name, Double kcal) {
        return new Menu(name, kcal);
    }
}
