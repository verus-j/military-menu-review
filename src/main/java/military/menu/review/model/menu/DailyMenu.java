package military.menu.review.model.menu;

import lombok.Getter;

import java.time.LocalDate;

@Getter
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
        breakfast.addMenu(menu);
    }

    public void addLunchMenu(Menu menu) {
        lunch.addMenu(menu);
    }

    public void addDinnerMenu(Menu menu) {
        dinner.addMenu(menu);
    }

    @Override
    public String toString() {
        return "DailyMenu{" +
                "date=" + date +
                ", breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                '}';
    }
}
