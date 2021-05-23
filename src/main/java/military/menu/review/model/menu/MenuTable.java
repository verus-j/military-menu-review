package military.menu.review.model.menu;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
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
}
