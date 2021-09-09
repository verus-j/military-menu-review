package military.menu.review.domain.meal;

import lombok.*;
import military.menu.review.domain.menu.MenuDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class MealDto {
    private Long id;
    private LocalDate date;
    private MealType mealType;
    private List<MenuDto> menus;

    public void addMenu(MenuDto menu) {
        menus.add(menu);
    }
}
