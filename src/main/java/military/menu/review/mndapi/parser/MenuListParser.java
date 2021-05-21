package military.menu.review.mndapi.parser;

import military.menu.review.model.Menu;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuListParser extends MndApiParser<List<Menu>>{
    public List<Menu> parse(String json) {
        return parseToList(parseToMap(json)).stream()
                .map(this::parseEachMenu)
                .flatMap(Collection::stream)
                .distinct()
                .filter(Menu::isNotEmpty)
                .collect(Collectors.toList());
    }

    private List<Menu> parseEachMenu(Map<String, String> jsonMap) {
        return Arrays.asList(
                BREAKFAST_CONVERTOR.convert(jsonMap),
                LUNCH_CONVERTOR.convert(jsonMap),
                DINNER_CONVERTOR.convert(jsonMap)
        );
    }
}
