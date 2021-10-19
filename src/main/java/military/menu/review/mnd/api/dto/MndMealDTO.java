package military.menu.review.mnd.api.dto;

import lombok.Getter;

@Getter
public class MndMealDTO {
    private String date;
    private MndMenuDTO breakfast;
    private MndMenuDTO lunch;
    private MndMenuDTO dinner;

    private MndMealDTO(String date, MndMenuDTO breakfast, MndMenuDTO lunch, MndMenuDTO dinner) {
        this.date = date;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public static MndMealDTO of(String date, MndMenuDTO breakfast, MndMenuDTO lunch, MndMenuDTO dinner) {
        return new MndMealDTO(date, breakfast, lunch, dinner);
    }
}
