package military.menu.review.ui.like;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.like.Like;
import military.menu.review.domain.member.Member;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter @Setter
public class LikeResponse extends RepresentationModel<LikeResponse> {
    private Long id;
    private Long memberId;
    private Long menuId;
    private LocalDateTime dateTime;

    public LikeResponse(Like like, Member member) {
        this.id = like.getId();
        this.memberId = like.getMember().getId();
        this.menuId = like.getMenu().getId();
        this.dateTime = like.getDateTime();

        add(selfLink());
        if(isCreatedMember(member)) {
            add(unlikeLink());
        }
    }

    private Link selfLink() {
        return linkTo(methodOn(LikeController.class, menuId).createLikes(null, null)).slash(id).withSelfRel();
    }

    private boolean isCreatedMember(Member member) {
        return member != null && member.getId().equals(memberId);
    }

    private Link unlikeLink() {
        return linkTo(methodOn(LikeController.class, menuId).cancel(null, null)).withRel("cancel-like");
    }
}
