package military.menu.review.ui.meal;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.meal.MealDto;
import military.menu.review.domain.meal.SelectedMenu;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuDto;
import military.menu.review.ui.menu.MenuResponse;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter @Setter
public class MealResponse extends RepresentationModel<MealResponse> {
    private Long id;
    private LocalDate date;
    private String mealType;
    private List<MenuResponse> menus;

    public MealResponse(MealDto mealDto) {
        this.id = mealDto.getId();
        this.date = mealDto.getDate();
        this.mealType = mealDto.getMealType().name();
        this.menus = mealDto.getMenus().stream().map(MenuResponse::new).collect(Collectors.toList());
        add(linkTo(MealController.class).slash(id).withSelfRel());
    }
}
