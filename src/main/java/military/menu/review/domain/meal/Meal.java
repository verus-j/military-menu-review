package military.menu.review.domain.meal;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Meal {
    @Id @GeneratedValue @Column(name="meal_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealType type;
    private LocalDate date;
    @ElementCollection
    private List<MealMenu> mealMenus = new ArrayList<>();

    protected Meal() {

    }

    private Meal(MealType type, LocalDate date) {
        this.type = type;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return type == meal.type && Objects.equals(date, meal.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, date);
    }

    public String toString() {
        return id + " " + type.name() + " " + date;
    }

    public static Meal of(MealType type, LocalDate date) {
        return new Meal(type, date);
    }
}
