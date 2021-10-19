package military.menu.review.ui.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.menu.MenuDto;
import military.menu.review.domain.menu.Menu;
import military.menu.review.ui.like.LikeController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter @Setter @Builder @AllArgsConstructor
public class MenuResponse extends RepresentationModel<MenuResponse> {
    private String name;
    private double kcal;
    private long like;
    private long id;

    public MenuResponse(MenuDto menuDto) {
        this.id = menuDto.getId();
        this.kcal = menuDto.getKcal();
        this.like = menuDto.getLike();
        this.name = menuDto.getName();
        add(linkTo(MenuController.class).slash(id).withSelfRel());

        if(menuDto.isLogin()) {
            if (!menuDto.isLiked()) {
                add(linkTo(methodOn(LikeController.class, id).createLikes(null, null)).withRel("like"));
            } else {
                add(linkTo(methodOn(LikeController.class, id).cancel(null, null)).withRel("cancel-like"));
            }
        }
    }
}
