package military.menu.review.model.menu;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuTable {
    private final List<DailyMenu> table;

    public MenuTable() {
        table = new ArrayList<>();
    }

    public DailyMenu getDailyMenu(int year, int month, int dayOfMonth) {
        return table.stream()
            .filter(dailyMenu -> dailyMenu.getDate().equals(LocalDate.of(year, month, dayOfMonth)))
            .findFirst().orElse(new DailyMenu(LocalDate.of(year, month, dayOfMonth)));
    }

    public void addDailyMenu(DailyMenu dailyMenu) {
        table.add(dailyMenu);
    }

    @Override
    public String toString() {
        return "MenuTable{" +
                "table=" + table +
                '}';
    }
}
