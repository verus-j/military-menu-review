package military.menu.review.domain.meal;

import lombok.*;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="meal_id")
    private Long id;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    protected Meal() {}

    private Meal(LocalDate date, MealType type) {
        this.date = date;
        this.mealType = type;
    }

    public static Meal of(LocalDate date, MealType type) {
        return new Meal(date, type);
    }
}
