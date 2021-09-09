package military.menu.review.ui.meal;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.meal.MealDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class MealsResponse extends RepresentationModel<MealsResponse> {
    List<MealResponse> meals;

    public MealsResponse(List<MealDto> meals, WeekRequest weekRequest) {
        this.meals = meals.stream().map(MealResponse::new).collect(Collectors.toList());
        addPrevWeekLink(weekRequest.prevWeek());
        addNextWeekLink(weekRequest.nextWeek());
        addSelfLink(weekRequest);
    }

    private void addSelfLink(WeekRequest weekRequest) {
        add(Link.of(
                String.format("/meals?year=%d&month=%d&week=%d", weekRequest.getYear(), weekRequest.getMonth(), weekRequest.getWeek()))
                .withSelfRel()
        );
    }

    private void addNextWeekLink(WeekRequest weekRequest) {
        add(Link.of(
                String.format("/meals?year=%d&month=%d&week=%d", weekRequest.getYear(), weekRequest.getMonth(), weekRequest.getWeek()))
                .withRel("next-week")
        );
    }

    private void addPrevWeekLink(WeekRequest weekRequest) {
        add(Link.of(
                String.format("/meals?year=%d&month=%d&week=%d", weekRequest.getYear(), weekRequest.getMonth(), weekRequest.getWeek()))
                .withRel("prev-week")
        );
    }
}
