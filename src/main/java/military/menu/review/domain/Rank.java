package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Ranks")
@Getter
public class Rank {
    @Id @GeneratedValue @Column(name="rank_id")
    private Long id;
    private LocalDate date;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_id")
    private Menu menu;
    @Column(name="likes")
    private Integer like;
    @Column(name="ranks")
    private Integer rank;

    protected Rank() {}

    private Rank(LocalDate date, Menu menu, Integer rank) {
        this.date = date;
        this.menu = menu;
        this.like = menu.getLike();
        this.rank = rank;
    }

    public static Rank of(LocalDate date, Menu menu, Integer rank) {
        return new Rank(date, menu, rank);
    }
}
