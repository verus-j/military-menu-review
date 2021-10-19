package military.menu.review.service.mnd.api.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.service.mnd.api.convertor.JsonToBreakfastConvertor;
import military.menu.review.service.mnd.api.convertor.JsonToDinnerConvertor;
import military.menu.review.service.mnd.api.convertor.JsonToLunchConvertor;
import military.menu.review.service.mnd.api.convertor.JsonToMenuConvertor;

import java.io.IOException;
import java.util.*;

public abstract class MndApiDataParser<T> {
    private static final String SERVICE_COLUMN = "DS_TB_MNDT_DATEBYMLSVC_ATC";
    private static final String ROW_COLUMN = "row";
    protected static final JsonToMenuConvertor BREAKFAST_CONVERTOR = new JsonToBreakfastConvertor();
    protected static final JsonToMenuConvertor LUNCH_CONVERTOR = new JsonToLunchConvertor();
    protected static final JsonToMenuConvertor DINNER_CONVERTOR = new JsonToDinnerConvertor();

    public abstract T parse(String json);

    protected List<Map<String, String>> deserializeByRow(String json) {
        return (List) deserializeByService(json).get(ROW_COLUMN);
    }

    protected Map<String, Object> deserializeByService(String json) {
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
