package military.menu.review.domain;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class MealMenuId implements Serializable {
    private Long meal;
    private Long menu;
}
