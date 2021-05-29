package military.menu.review.model.menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@ToString
public class DailyMenu {
    private final LocalDate date;
    private final MenuList breakfast;
    private final MenuList lunch;
    private final MenuList dinner;

    public DailyMenu(LocalDate date) {
        this.date = date;
        breakfast = new MenuList();
        lunch = new MenuList();
        dinner = new MenuList();
    }

    public void addBreakfastMenu(Menu menu) {
        breakfast.add(menu);
    }

    public void addLunchMenu(Menu menu) {
        lunch.add(menu);
    }

    public void addDinnerMenu(Menu menu) {
        dinner.add(menu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMenu dailyMenu = (DailyMenu) o;
        return Objects.equals(date, dailyMenu.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
