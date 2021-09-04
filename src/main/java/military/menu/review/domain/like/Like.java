package military.menu.review.domain.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="Likes")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Like {
    @Id @GeneratedValue @Column(name="like_id")
    private Long id;
    private Long menuId;
    private Long memberId;
    private LocalDateTime dateTime;
}
