package military.menu.review.mndapi.parser;

public class TotalCountParser extends MndApiDataParser<Integer> {
    private static final String TOTAL_COUNT_COLUMN = "list_total_count";

    public Integer parse(String json) {
        return (Integer) destructToService(json).get(TOTAL_COUNT_COLUMN);
    }
}
