package military.menu.review.ui.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import military.menu.review.application.menu.MenuDto;
import military.menu.review.domain.menu.Menu;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter @Setter @Builder @AllArgsConstructor
public class MenuResponse extends RepresentationModel<MenuResponse> {
    private String name;
    private double kcal;
    private long like;
    private long id;

    public MenuResponse(MenuDto menuDto) {

    }

    public MenuResponse(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.kcal = menu.getKcal();
        this.like = menu.getLike();
        add(linkTo(MenuController.class).slash(id).withSelfRel());
//        add(linkTo(MenuController.class).slash(id).slash("like").withRel("like"));
//        add(linkTo(MenuController.class).slash(id).slash("unlike").withRel("unlike"));
    }
}
