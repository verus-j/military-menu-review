package military.menu.review.mndapi.convertor;

public class JsonToDinnerConvertor extends JsonToMenuConvertor{
    @Override
    public String getMenuNameColumn() {
        return "dinr";
    }

    @Override
    public String getCalorieColumn() {
        return "dinr_cal";
    }
}
