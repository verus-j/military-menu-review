package military.menu.review.model.menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
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
}
