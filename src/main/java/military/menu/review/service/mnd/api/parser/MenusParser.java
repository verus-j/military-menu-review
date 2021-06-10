package military.menu.review.service.mnd.api.parser;

import military.menu.review.service.dto.MenuDTO;

import java.util.*;
import java.util.stream.Collectors;

public class MenusParser extends MndApiDataParser<List<MenuDTO>> {
    public List<MenuDTO> parse(String json) {
        return deserializeByRow(json).stream()
                .map(this::parseEachMenu)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<MenuDTO> parseEachMenu(Map<String, String> jsonMap) {
        List<MenuDTO> result = new ArrayList<>();
        BREAKFAST_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        LUNCH_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        DINNER_CONVERTOR.convert(jsonMap).ifPresent(result::add);
        return result;
    }
}
