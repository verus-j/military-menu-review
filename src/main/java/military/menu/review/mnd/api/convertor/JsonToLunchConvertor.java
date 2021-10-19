package military.menu.review.mnd.api.convertor;

public class JsonToLunchConvertor extends JsonToMenuConvertor{
    @Override
    public String getMenuNameColumn() {
        return "lunc";
    }

    @Override
    public String getCalorieColumn() {
        return "lunc_cal";
    }
}
