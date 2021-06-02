package military.menu.review.mndapi.parser;

import military.menu.review.model.menu.Menu;

import java.util.List;

public class TotalCountParser extends MndApiDataParser<Integer> {
    private static final String TOTAL_COUNT_COLUMN = "list_total_count";

    public Integer parse(String json) {
        return (Integer) destructToService(json).get(TOTAL_COUNT_COLUMN);
    }
}
