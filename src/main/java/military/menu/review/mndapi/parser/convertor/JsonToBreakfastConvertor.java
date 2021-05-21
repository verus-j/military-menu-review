package military.menu.review.mndapi.parser.convertor;

import military.menu.review.mndapi.parser.convertor.JsonToMenuConvertor;
import military.menu.review.model.Menu;

import java.util.Map;

public class JsonToBreakfastConvertor extends JsonToMenuConvertor {
    @Override
    public String getMenuNameColumn() {
        return "brst";
    }

    @Override
    public String getCalorieColumn() {
        return "brst_cal";
    }
}
