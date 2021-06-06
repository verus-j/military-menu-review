package military.menu.review.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="Likes")
public class Like {
    @Id @GeneratedValue @Column(name="like_id")
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_id")
    private Menu menu;

    protected Like() {}

    private Like(User user, Menu menu) {
        this.user = user;
        this.menu = menu;
    }

    public static Like of(User user, Menu menu) {
        return new Like(user, menu);
    }
}
