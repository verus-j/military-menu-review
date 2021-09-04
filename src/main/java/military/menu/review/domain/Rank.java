package military.menu.review.domain;

import lombok.Getter;
import military.menu.review.domain.menu.Menu;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="Ranks")
@Getter
public class Rank {
    @Id @GeneratedValue @Column(name="rank_id")
    private Long id;
    @Embedded
    private Week week;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_id")
    private Menu menu;
    @Column(name="likes")
    private Long like;
    @Column(name="ranks")
    private Integer rank;

    protected Rank() {}

    private Rank(Week week, Menu menu, Long like, Integer rank) {
        this.week = week;
        this.menu = menu;
        this.like = like;
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank1 = (Rank) o;
        return Objects.equals(week, rank1.week) && Objects.equals(menu, rank1.menu) && Objects.equals(like, rank1.like) && Objects.equals(rank, rank1.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(week, menu, like, rank);
    }

    public static Rank of(Week date, Menu menu, Integer rank) {
        return new Rank(date, menu, menu.getLike(), rank);
    }
}
