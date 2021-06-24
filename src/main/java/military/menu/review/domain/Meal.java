package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Meal {
    @Id @GeneratedValue @Column(name="meal_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealType type;
    private LocalDate date;
    @OneToMany(mappedBy="meal")
    private List<MealMenu> mealMenus = new ArrayList<>();

    protected Meal() {

    }

    private Meal(MealType type, LocalDate date) {
        this.type = type;
        this.date = date;
    }

    public String toString() {
        return id + " " + type.name() + " " + date;
    }

    public static Meal of(MealType type, LocalDate date) {
        return new Meal(type, date);
    }
}
