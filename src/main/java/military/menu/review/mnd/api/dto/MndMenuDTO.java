package military.menu.review.mnd.api.dto;

import lombok.Getter;

@Getter
public class MndMenuDTO {
    private String name;
    private double kcal;

    public MndMenuDTO(String name, double kcal) {
        this.name = name;
        this.kcal = kcal;
    }

    public static MndMenuDTO of(String name, double kcal) {
        return new MndMenuDTO(name, kcal);
    }
}
