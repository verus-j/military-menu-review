package military.menu.review.mndapi.parser.convertor;

import military.menu.review.model.Menu;

import java.util.Map;
import java.util.Optional;

public abstract class JsonToMenuConvertor {
    private static final String MENU_NAME_REDUNDANT_PATTERN = "\\(.*\\)";
    private static final String CALORIE_PATTERN = "\\d+(\\.\\d+)?kcal";
    private static final String CALORIE_REDUNDANT_PATTERN = "kcal";

    public abstract String getMenuNameColumn();
    public abstract String getCalorieColumn();

    public Optional<Menu> convert(Map<String, String> jsonMap){
        String menuName = jsonMap.get(getMenuNameColumn());
        String calorie = jsonMap.get(getCalorieColumn());
        return parseMenu(menuName, calorie);
    }

    private Optional<Menu> parseMenu(String name, String calorie) {
        return isEmptyMenu(name) ? Optional.empty() : Optional.of(Menu.of(parseMenuName(name), parseCalorie(calorie)));
    }

    private boolean isEmptyMenu(String name) {
        return (name.trim().equals("") || name.trim().length() == 0);
    }

    private String parseMenuName(String name) {
        return name.replaceAll(MENU_NAME_REDUNDANT_PATTERN, "");
    }

    private double parseCalorie(String calorie) {
        if(calorie.matches(CALORIE_PATTERN))
            return Double.parseDouble(calorie.replaceAll(CALORIE_REDUNDANT_PATTERN, ""));

        return 0.0;
    }
}
