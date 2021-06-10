package military.menu.review.service.mnd.api.parser;

import military.menu.review.service.dto.DailyMealDTO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DailyMealsParser extends MndApiDataParser<List<DailyMealDTO>> {
    private final String DATE_COLUMN = "dates";
    private Map<String, DailyMealDTO> dateMap = new HashMap<>();

    public List<DailyMealDTO> parse(String json) {
        for(Map<String, String> jsonMap : deserializeByRow(json)) {
            if(isNewDate(jsonMap)) {
                dateMap.put(jsonMap.get(DATE_COLUMN), createDailyMeal(jsonMap));
            }

            DailyMealDTO dailyMeal = dateMap.get(jsonMap.get(DATE_COLUMN));

            if(dailyMeal != null) {
                addEachMenu(dailyMeal, jsonMap);
            }
        }

        return dateMap.keySet().stream().map(k -> dateMap.get(k)).collect(Collectors.toList());
    }

    private boolean isNewDate(Map<String, String> jsonMap) {
        return !jsonMap.get(DATE_COLUMN).trim().equals("") && !dateMap.containsKey(jsonMap.get(DATE_COLUMN));
    }

    private DailyMealDTO createDailyMeal(Map<String, String> jsonMap) {
        return DailyMealDTO.of(LocalDate.parse(jsonMap.get(DATE_COLUMN)));
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
