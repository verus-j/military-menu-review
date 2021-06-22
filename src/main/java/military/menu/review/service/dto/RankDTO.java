package military.menu.review.service.dto;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;

import javax.persistence.*;

@Getter
@Setter
public class RankDTO implements Comparable<RankDTO>{
    private String menu;
    private int like;
    private int rank;

    public RankDTO(Menu menu, int rank) {
        this.menu = menu.getName();
        this.like = menu.getLike();
        this.rank = rank;
    }

    public RankDTO(Rank rank) {
        this.menu = rank.getMenu().getName();
        this.like = rank.getLike();
        this.rank = rank.getRank();
    }

    @Override
    public int compareTo(RankDTO o) {
        return rank < o.rank ? -1 : (rank > o.rank ? 1 : 0);
    }
}
