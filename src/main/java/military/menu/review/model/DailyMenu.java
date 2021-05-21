package military.menu.review.model;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DailyMenu {
    @Getter
    private static class HasOrderMenu extends Menu implements Comparable<HasOrderMenu>{
        private final int order;

        public HasOrderMenu(Menu menu, int order) {
            super(menu.name, menu.calorie);
            this.order = order;
        }

        @Override
        public String toString() {
            return "HasOrderMenu{" +
                    "order=" + order +
                    ", name='" + name + '\'' +
                    ", calorie=" + calorie +
                    '}';
        }


        @Override
        public int compareTo(HasOrderMenu o) {
            return Integer.compare(order, o.order);
        }
    }

    private final LocalDate date;
    private final List<Menu> breakfast;
    private final List<Menu> lunch;
    private final List<Menu> dinner;

    public DailyMenu(LocalDate date) {
        this.date = date;
        breakfast = new ArrayList<>();
        lunch = new ArrayList<>();
        dinner = new ArrayList<>();
    }

    public void addBreakfastMenu(Menu menu) {
        breakfast.add(new HasOrderMenu(menu, breakfast.size()));
    }

    public void addLunchMenu(Menu menu) {
        lunch.add(new HasOrderMenu(menu, lunch.size()));
    }

    public void addDinnerMenu(Menu menu) {
        dinner.add(new HasOrderMenu(menu, dinner.size()));
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
