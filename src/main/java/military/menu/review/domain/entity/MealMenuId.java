package military.menu.review.domain.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class MealMenuId implements Serializable {
    private Long meal;
    private Long menu;
}
