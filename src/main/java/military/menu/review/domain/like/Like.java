package military.menu.review.domain.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="Likes")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
    private LocalDateTime dateTime;
}
