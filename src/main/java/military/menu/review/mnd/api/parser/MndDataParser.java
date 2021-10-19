package military.menu.review.mnd.api.parser;

import military.menu.review.domain.meal.Meal;
import military.menu.review.mnd.api.dto.MndMenuDTO;
import military.menu.review.mnd.api.dto.MndMealDTO;
import java.util.*;

public class MndDataParser extends MndApiDataParser<List<MndMealDTO>> {
    private final String DATE_COLUMN = "dates";
    private Map<String, Meal> dateMap = new HashMap<>();

    public List<MndMealDTO> parse(String json) {
        List<MndMealDTO> result = new ArrayList<>();

        for(Map<String, String> jsonMap : deserializeByRow(json)) {
            String date = getDate(jsonMap);
            MndMenuDTO breakfast = BREAKFAST_CONVERTOR.convert(jsonMap).orElse(null);
            MndMenuDTO lunch = LUNCH_CONVERTOR.convert(jsonMap).orElse(null);
            MndMenuDTO dinner = DINNER_CONVERTOR.convert(jsonMap).orElse(null);
            result.add(MndMealDTO.of(date, breakfast, lunch, dinner));
        }

        return result;
    }

    private String getDate(Map<String, String> jsonMap) {
        return jsonMap.get(DATE_COLUMN).trim();
    }
}
