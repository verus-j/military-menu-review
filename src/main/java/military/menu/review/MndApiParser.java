package military.menu.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.model.Menu;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MndApiParser {
    private static final String TOTAL_COUNT_COLUMN = "list_total_count";
    private static final String DATA_TABLE_COLUMN = "DS_TB_MNDT_DATEBYMLSVC_ATC";
    private static final String MENU_LIST_COLUMN = "row";
    private static final String LUNCH_COLUMN = "lunc";
    private static final String LUNCH_CALORIE_COLUMN = "lunc_cal";
    private static final String DINNER_COLUMN = "dinr";
    private static final String DINNER_CALORIE_COLUMN = "dinr_cal";
    private static final String BREAKFAST_COLUMN = "brst";
    private static final String BREAKFAST_CALORIE_COLUMN = "brst_cal";
    private static final String MENU_NAME_REDUNDANT_PATTERN = "\\(.*\\)";
    private static final String CALORIE_REDUNDANT_PATTERN = "kcal";

    public int totalCount(String json) {
        return (int)parseMap(json).get(TOTAL_COUNT_COLUMN);
    }

    private Map<String, Object> parseMap(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(json, Map.class);
            return (Map<String, Object>)map.get(DATA_TABLE_COLUMN);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Menu> menuList(String json) {
        return parseTable(parseMap(json)).stream()
                .map(this::parseDailyMenu)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> parseTable(Map<String, Object> map) {
        return (List)map.get(MENU_LIST_COLUMN);
    }

    private List<Menu> parseDailyMenu(Map<String, String> dailyMenu) {
        return Arrays.asList(
                parseBreakfastMenu(dailyMenu),
                parseLunchMenu(dailyMenu),
                parseDinnerMenu(dailyMenu)
        );
    }

    private Menu parseBreakfastMenu(Map<String, String> dailyMenu) {
        return parseMenu(dailyMenu.get(BREAKFAST_COLUMN), dailyMenu.get(BREAKFAST_CALORIE_COLUMN));
    }

    private Menu parseLunchMenu(Map<String, String> dailyMenu) {
        return parseMenu(dailyMenu.get(LUNCH_COLUMN), dailyMenu.get(LUNCH_CALORIE_COLUMN));
    }

    private Menu parseDinnerMenu(Map<String, String> dailyMenu) {
        return parseMenu(dailyMenu.get(DINNER_COLUMN), dailyMenu.get(DINNER_CALORIE_COLUMN));
    }

    private Menu parseMenu(String name, String calorie) {
        return Menu.of(parseMenuName(name), parseCalorie(calorie));
    }

    private String parseMenuName(String name) {
        return name.replaceAll(MENU_NAME_REDUNDANT_PATTERN, "");
    }

    private double parseCalorie(String calorie) {
        return Double.parseDouble(calorie.replaceAll(CALORIE_REDUNDANT_PATTERN, ""));
    }
}
