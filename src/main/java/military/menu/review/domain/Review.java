package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Review {
    @Id @GeneratedValue @Column(name="review_id")
    private Long id;
    private String content;
    private LocalDate created;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="meal_id")
    private Meal meal;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    protected Review() {}

    private Review(String content, LocalDate created, Meal meal, User user) {
        this.content = content;
        this.created = created;
        this.meal = meal;
        this.user = user;
    }

    public static Review of(String content, LocalDate created, Meal meal, User user) {
        return new Review(content, created, meal, user);
    }
}
