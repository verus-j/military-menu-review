package military.menu.review.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Meal {
    @Id @GeneratedValue @Column(name="meal_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealType type;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="daily_meal_id")
    private DailyMeal dailyMeal;
    @OneToMany(mappedBy="meal", fetch=FetchType.LAZY)
    private List<MealMenu> mealMenus = new ArrayList<>();

    protected Meal() {

    }

    private Meal(MealType type, DailyMeal dailyMeal) {
        this.type = type;
        this.dailyMeal = dailyMeal;
    }

    public String toString() {
        return id + " " + type.name() + " " + dailyMeal.getDate();
    }

    public static Meal of(MealType type, DailyMeal dailyMeal) {
        return new Meal(type, dailyMeal);
    }
}
