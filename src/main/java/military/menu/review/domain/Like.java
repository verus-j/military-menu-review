package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="Likes")
public class Like {
    @Id @GeneratedValue @Column(name="like_id")
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="member_id")
    private Member member;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_id")
    private Menu menu;

    protected Like() {}

    private Like(Member member, Menu menu) {
        this.member = member;
        this.menu = menu;
    }

    public static Like of(Member member, Menu menu) {
        return new Like(member, menu);
    }
}
