package military.menu.review.service.dto;

import lombok.Getter;
import military.menu.review.domain.Member;
import military.menu.review.domain.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewDTO {
    private long id;
    private String content;
    private String name;
    private LocalDateTime created;
    private boolean isMine;

    public ReviewDTO(Review review, Member member) {
        this.id = review.getId();
        this.content = review.getContent();
        this.name = review.getMember().getName();
        this.created = review.getCreated();
        this.isMine = review.getMember().getId() == member.getId();
    }
}
