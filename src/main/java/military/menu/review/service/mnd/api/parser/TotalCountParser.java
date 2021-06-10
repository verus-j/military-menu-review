package military.menu.review.service.mnd.api.parser;

public class TotalCountParser extends MndApiDataParser<Integer> {
    private static final String TOTAL_COUNT_COLUMN = "list_total_count";

    public Integer parse(String json) {
        return (Integer) deserializeByService(json).get(TOTAL_COUNT_COLUMN);
    }
}
