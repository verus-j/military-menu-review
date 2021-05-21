package military.menu.review.mndapi.parser.convertor;

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
