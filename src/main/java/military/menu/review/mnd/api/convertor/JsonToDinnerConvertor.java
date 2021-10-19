package military.menu.review.service.mnd.api.convertor;

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
