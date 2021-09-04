package military.menu.review.domain.menu;

import lombok.Getter;
import military.menu.review.domain.member.Member;

import javax.persistence.*;

@Entity
@Getter
@Table(name="Likes")
public class Like {
    @Id @GeneratedValue @Column(name="like_id")
    private Long id;
    @ManyToOne @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne @JoinColumn(name="menu_id")
    private Menu menu;

    protected Like() {}

    private Like(Menu menu, Member member) {
        this.member = member;
        this.menu = menu;
    }

    public static Like of(Menu menu, Member member) {
        return new Like(menu, member);
    }
}
