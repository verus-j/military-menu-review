package military.menu.review.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class DailyMeal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="daily_meal_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private LocalDate date;
    @OneToMany(mappedBy="dailyMeal", fetch=FetchType.LAZY)
    private List<Meal> meals = new ArrayList<>();

    protected DailyMeal() {}

    private DailyMeal(LocalDate date) {
        this.date = date;
    }

    public static DailyMeal from(LocalDate date) {
        return new DailyMeal(date);
    }
}
