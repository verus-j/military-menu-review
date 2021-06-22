package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
public class DailyMeal {
    @Id @GeneratedValue @Column(name="daily_meal_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private LocalDate date;
    @OneToMany(mappedBy="dailyMeal", fetch=FetchType.LAZY)
    private List<Meal> meals = new ArrayList<>();

    protected DailyMeal() {}

    private DailyMeal(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMeal dailyMeal = (DailyMeal) o;
        return Objects.equals(id, dailyMeal.id) && Objects.equals(date, dailyMeal.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    public static DailyMeal from(LocalDate date) {
        return new DailyMeal(date);
    }
}
