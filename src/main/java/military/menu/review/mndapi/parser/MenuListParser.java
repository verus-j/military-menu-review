package military.menu.review.mndapi.parser;

import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;

import java.util.*;

public class MenuListParser extends MndApiDataParser<MenuList> {
    public MenuList parse(String json) {
        MenuList list = new MenuList();
        destructToMenuList(json).stream()
                .map(this::parseEachMenu)
                .flatMap(Collection::stream)
                .distinct()
                .forEach(list::add);
        return list;
    }

    private List<Menu> parseEachMenu(Map<String, String> jsonMap) {
        List<Menu> result = new ArrayList<>();
        BREAKFAST_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        LUNCH_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        DINNER_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        return result;
    }
}
