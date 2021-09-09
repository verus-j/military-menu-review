package military.menu.review.domain.meal;

import lombok.*;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter @Getter
public class SelectedMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="selected_menu_id")
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_id")
    private Menu menu;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="meal_id")
    private Meal meal;

    protected SelectedMenu() {}

    public SelectedMenu(Meal meal, Menu menu) {
        this.meal = meal;
        this.menu = menu;
    }

    public static SelectedMenu of(Meal meal, Menu menu) {
        return new SelectedMenu(meal, menu);
    }
}
