package military.menu.review.model.menu;

import java.time.LocalDate;

public class DailyMenu {
    private LocalDate date;
    private MenuList breakfast;
    private MenuList lunch;
    private MenuList dinner;

    public DailyMenu(LocalDate date) {
        this.date = date;
        breakfast = new MenuList();
        lunch = new MenuList();
        dinner = new MenuList();
    }

    public LocalDate getDate() {
        return date;
    }

    public MenuList getBreakfast() {
        return breakfast;
    }

    public MenuList getLunch() {
        return lunch;
    }

    public MenuList getDinner() {
        return dinner;
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
