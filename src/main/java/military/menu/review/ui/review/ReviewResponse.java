package military.menu.review.ui.review;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.review.Review;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter @Setter
public class ReviewResponse extends RepresentationModel<ReviewResponse> {
    private Long id;
    private Long mealId;
    private Long memberId;
    private String content;
    private LocalDateTime created;

    public ReviewResponse(Review review, Member member) {
        this.id = review.getId();
        this.mealId = review.getMeal().getId();
        this.memberId = review.getMember().getId();
        this.content = review.getContent();
        this.created = review.getCreated();

        add(linkTo(ReviewController.class, mealId).slash(id).withSelfRel());
        add(linkTo(ReviewController.class, mealId).withRel("reviews"));

        if(member != null) {
            if (member.getId().equals(memberId)) {
                add(linkTo(ReviewController.class, mealId).slash(id).withRel("update-review"));
                add(linkTo(ReviewController.class, mealId).slash(id).withRel("delete-review"));
            }
        }
    }
}
