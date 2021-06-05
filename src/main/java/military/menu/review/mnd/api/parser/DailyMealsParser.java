package military.menu.review.mnd.api.parser;

import military.menu.review.domain.dto.DailyMealDTO;

import java.time.LocalDate;
import java.util.*;

public class DailyMealsParser extends MndApiDataParser<List<DailyMealDTO>> {
    private final String MENU_DATE_COLUMN = "dates";
    private Set<String> dateHistory = new HashSet<>();

    public List<DailyMealDTO> parse(String json) {
        List<DailyMealDTO> result = new ArrayList<>();
        DailyMealDTO dailyMeal = null;

        for(Map<String, String> jsonMap : destructToMenuList(json)) {
            if(isNewDate(jsonMap)) {
                dateHistory.add(jsonMap.get(MENU_DATE_COLUMN));
                dailyMeal = createDailyMeal(jsonMap);
                result.add(dailyMeal);
            }

            if(dailyMeal != null) {
                addEachMenu(dailyMeal, jsonMap);
            }
        }

        return result;
    }

    private boolean isNewDate(Map<String, String> jsonMap) {
        return !jsonMap.get(MENU_DATE_COLUMN).trim().equals("") && !dateHistory.contains(jsonMap.get(MENU_DATE_COLUMN));
    }

    private DailyMealDTO createDailyMeal(Map<String, String> jsonMap) {
        return DailyMealDTO.of(LocalDate.parse(jsonMap.get(MENU_DATE_COLUMN)));
    }

    private void addEachMenu(DailyMealDTO dailyDate, Map<String, String> jsonMap) {
        addBreakfast(dailyDate, jsonMap);
        addLunch(dailyDate, jsonMap);
        addDinner(dailyDate, jsonMap);
    }

    private void addBreakfast(DailyMealDTO dailyDate, Map<String, String> jsonMap) {
        BREAKFAST_CONVERTOR.convert(jsonMap).ifPresent(dailyDate::addBreakfastMenu);
    }

    private void addLunch(DailyMealDTO dailyDate, Map<String, String> jsonMap) {
        LUNCH_CONVERTOR.convert(jsonMap).ifPresent(dailyDate::addLunchMenu);
    }

    private void addDinner(DailyMealDTO dailyDate, Map<String, String> jsonMap) {
        DINNER_CONVERTOR.convert(jsonMap).ifPresent(dailyDate::addDinnerMenu);
    }
}
