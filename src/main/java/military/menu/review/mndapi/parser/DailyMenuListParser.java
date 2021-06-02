package military.menu.review.mndapi.parser;

import military.menu.review.model.menu.DailyMenu;

import java.time.LocalDate;
import java.util.*;

public class DailyMenuListParser extends MndApiDataParser<List<DailyMenu>> {
    private final String MENU_DATE_COLUMN = "dates";
    private Set<String> dateHistory = new HashSet<>();

    public List<DailyMenu> parse(String json) {
        List<DailyMenu> dailyMenuList = new ArrayList<>();
        Optional<DailyMenu> dailyMenu = Optional.ofNullable(null);

        for(Map<String, String> jsonMap : destructToMenuList(json)) {
            if(isNewDate(jsonMap)) {
                dateHistory.add(jsonMap.get(MENU_DATE_COLUMN));
                dailyMenu = createDailyMenu(jsonMap);
                dailyMenuList.add(dailyMenu.get());
            }

            dailyMenu.ifPresent(menu -> addEachMenu(menu, jsonMap));
        }

        return dailyMenuList;
    }

    private boolean isNewDate(Map<String, String> jsonMap) {
        return !jsonMap.get(MENU_DATE_COLUMN).trim().equals("") && !dateHistory.contains(jsonMap.get(MENU_DATE_COLUMN));
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
