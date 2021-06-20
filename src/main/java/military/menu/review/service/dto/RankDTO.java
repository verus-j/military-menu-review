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
    private int year;
    private int month;
    private int week;
    private String menu;
    private int like;
    private int rank;

    public RankDTO(Rank rank) {
        this.year = rank.getWeek().year();
        this.month = rank.getWeek().month();
        this.week = rank.getWeek().week();
        this.menu = rank.getMenu().getName();
        this.like = rank.getMenu().getLike();
        this.rank = rank.getRank();
    }

    @Override
    public int compareTo(RankDTO o) {
        return rank < o.rank ? -1 : (rank > o.rank ? 1 : 0);
    }
}
