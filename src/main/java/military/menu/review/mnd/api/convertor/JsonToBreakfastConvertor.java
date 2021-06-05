package military.menu.review.mnd.api.convertor;

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
