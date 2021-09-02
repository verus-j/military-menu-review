package military.menu.review.domain;

import lombok.Getter;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
public class Review {
    @Id @GeneratedValue @Column(name="review_id")
    private Long id;
    private String content;
    private LocalDateTime created;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="meal_id")
    private Meal meal;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="member_id")
    private Member member;

    protected Review() {}

    private Review(String content, LocalDateTime created, Meal meal, Member member) {
        this.content = content;
        this.created = created;
        this.meal = meal;
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(content, review.content) && Objects.equals(created, review.created) && Objects.equals(meal, review.meal) && Objects.equals(member, review.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, created, meal, member);
    }

    public static Review of(String content, LocalDateTime created, Meal meal, Member member) {
        return new Review(content, created, meal, member);
    }
}
