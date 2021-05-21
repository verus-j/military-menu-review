package military.menu.review.mndapi.parser.convertor;

import military.menu.review.model.Menu;

import java.util.Map;

public abstract class JsonToMenuConvertor {
    private static final String MENU_NAME_REDUNDANT_PATTERN = "\\(.*\\)";
    private static final String CALORIE_PATTERN = "\\d+(\\.\\d+)?kcal";
    private static final String CALORIE_REDUNDANT_PATTERN = "kcal";

    public abstract String getMenuNameColumn();
    public abstract String getCalorieColumn();

    public Menu convert(Map<String, String> jsonMap){
        String menuName = jsonMap.get(getMenuNameColumn());
        String calorie = jsonMap.get(getCalorieColumn());
        return parseMenu(menuName, calorie);
    }

    private Menu parseMenu(String name, String calorie) {
        return Menu.of(parseMenuName(name), parseCalorie(calorie));
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
