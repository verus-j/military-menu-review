package military.menu.review.mndapi.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.mndapi.convertor.JsonToBreakfastConvertor;
import military.menu.review.mndapi.convertor.JsonToDinnerConvertor;
import military.menu.review.mndapi.convertor.JsonToLunchConvertor;
import military.menu.review.mndapi.convertor.JsonToMenuConvertor;
import military.menu.review.model.menu.Menu;

import java.io.IOException;
import java.util.*;

public abstract class MndApiDataParser<T> {
    private static final String SERVICE_COLUMN = "DS_TB_MNDT_DATEBYMLSVC_ATC";
    private static final String LIST_COLUMN = "row";
    protected static final JsonToMenuConvertor BREAKFAST_CONVERTOR = new JsonToBreakfastConvertor();
    protected static final JsonToMenuConvertor LUNCH_CONVERTOR = new JsonToLunchConvertor();
    protected static final JsonToMenuConvertor DINNER_CONVERTOR = new JsonToDinnerConvertor();

    public abstract T parse(String json);

    protected List<Map<String, String>> destructToMenuList(String json) {
        return (List) destructToService(json).get(LIST_COLUMN);
    }

    protected Map<String, Object> destructToService(String json) {
        return (Map<String, Object>)readJson(json).get(SERVICE_COLUMN);
    }

    private Map<String, Object> readJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
