package military.menu.review.mndapi.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.mndapi.parser.convertor.JsonToBreakfastConvertor;
import military.menu.review.mndapi.parser.convertor.JsonToDinnerConvertor;
import military.menu.review.mndapi.parser.convertor.JsonToLunchConvertor;
import military.menu.review.mndapi.parser.convertor.JsonToMenuConvertor;
import java.util.*;

public abstract  class MndApiParser<T> {
    private static final String DATA_TABLE_COLUMN = "DS_TB_MNDT_DATEBYMLSVC_ATC";
    private static final String MENU_LIST_COLUMN = "row";
    protected static final JsonToMenuConvertor BREAKFAST_CONVERTOR = new JsonToBreakfastConvertor();
    protected static final JsonToMenuConvertor LUNCH_CONVERTOR = new JsonToLunchConvertor();
    protected static final JsonToMenuConvertor DINNER_CONVERTOR = new JsonToDinnerConvertor();

    public abstract T parse(String json);

    protected Map<String, Object> parseToMap(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(json, Map.class);
            return (Map<String, Object>)map.get(DATA_TABLE_COLUMN);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Map<String, String>> parseToList(Map<String, Object> map) {
        return (List)map.get(MENU_LIST_COLUMN);
    }
}
