package military.menu.review.mndapi.parser;

import military.menu.review.model.Menu;

import java.util.*;
import java.util.stream.Collectors;

public class MenuListParser extends MndApiParser<List<Menu>>{
    public List<Menu> parse(String json) {
        return destructToMenuList(json).stream()
                .map(this::parseEachMenu)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Menu> parseEachMenu(Map<String, String> jsonMap) {
        List<Menu> result = new ArrayList<>();
        BREAKFAST_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        LUNCH_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        DINNER_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        return result;
    }
}
