package military.menu.review.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.MealMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @EqualsAndHashCode
public class MealDTO {
    private Long id;
    private List<MenuDTO> menus;

    private MealDTO() {
        id = null;
        menus = new ArrayList<>();
    }

    public MealDTO(Meal meal) {
        this.id = meal.getId();
        this.menus = meal.getMealMenus().stream()
                .sorted().map(MealMenu::getMenu).map(MenuDTO::new).collect(Collectors.toList());
    }

    public void addMenu(MenuDTO menu) {
        menus.add(menu);
    }

    public static MealDTO empty() {
        return new MealDTO();
    }
}
