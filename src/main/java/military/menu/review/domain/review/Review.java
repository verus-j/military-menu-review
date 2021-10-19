package military.menu.review.domain.review;

import lombok.*;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="review_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="meal_id")
    private Meal meal;
    private String content;
    private LocalDateTime created;

    protected Review() {}

    private Review(Member member, Meal meal, String content) {
        this.member = member;
        this.meal = meal;
        this.content = content;
        this.created = LocalDateTime.now();
    }

    public void editContent(String content) {
        this.content = content;
        this.created = LocalDateTime.now();
    }

    public static Review of(Member member, Meal meal, String content) {
        return new Review(member, meal, content);
    }
}
