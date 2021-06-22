package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;

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
    private Integer like;
    @Column(name="ranks")
    private Integer rank;

    protected Rank() {}

    private Rank(Week week, Menu menu, Integer like, Integer rank) {
        this.week = week;
        this.menu = menu;
        this.like = like;
        this.rank = rank;
    }

    public static Rank of(Week date, Menu menu, int like, Integer rank) {
        return new Rank(date, menu, like, rank);
    }
}
