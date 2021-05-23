package military.menu.review.mndapi.parser;

import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.MenuTable;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class MenuTableParser extends MndApiParser<MenuTable>{
    private final String MENU_DATE_COLUMN = "dates";

    public MenuTable parse(String json) {
        MenuTable menuTable = new MenuTable();
        Optional<DailyMenu> dailyMenu = Optional.ofNullable(null);

        for(Map<String, String> jsonMap : destructToMenuList(json)) {
            if(hasDate(jsonMap)) {
                dailyMenu = createDailyMenu(jsonMap);
                menuTable.addDailyMenu(dailyMenu.get());
            }

            dailyMenu.ifPresent(menu -> addEachMenu(menu, jsonMap));
        }

        return menuTable;
    }

    private boolean hasDate(Map<String, String> jsonMap) {
        return !jsonMap.get(MENU_DATE_COLUMN).trim().equals("");
    }

    private Optional<DailyMenu> createDailyMenu(Map<String, String> jsonMap) {
        return Optional.of(new DailyMenu(LocalDate.parse(jsonMap.get(MENU_DATE_COLUMN))));
    }

    private void addEachMenu(DailyMenu dailyMenu, Map<String, String> jsonMap) {
        addBreakfast(dailyMenu, jsonMap);
        addLunch(dailyMenu, jsonMap);
        addDinner(dailyMenu, jsonMap);
    }

    private void addBreakfast(DailyMenu dailyMenu, Map<String, String> jsonMap) {
        BREAKFAST_CONVERTOR.convert(jsonMap).ifPresent(dailyMenu::addBreakfastMenu);
    }

    private void addLunch(DailyMenu dailyMenu, Map<String, String> jsonMap) {
        LUNCH_CONVERTOR.convert(jsonMap).ifPresent(dailyMenu::addLunchMenu);
    }

    private void addDinner(DailyMenu dailyMenu, Map<String, String> jsonMap) {
        DINNER_CONVERTOR.convert(jsonMap).ifPresent(dailyMenu::addDinnerMenu);
    }
}
