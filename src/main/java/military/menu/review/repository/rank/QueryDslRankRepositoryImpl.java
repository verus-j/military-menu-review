package military.menu.review.repository.rank;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.QMenu;
import military.menu.review.domain.QRank;
import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static military.menu.review.domain.QMenu.menu;
import static military.menu.review.domain.QRank.rank1;

@Repository
@RequiredArgsConstructor
public class QueryDslRankRepositoryImpl implements QueryDslRankRepository{
    private final EntityManager em;


    @Override
    public List<Rank> findByWeekOrderByRankLimit(Week week, int limit) {
        JPAQuery query = new JPAQuery(em);

        return query.from(rank1)
            .join(rank1.menu, menu).fetch()
            .where(rank1.week.eq(week))
            .orderBy(rank1.rank.asc())
            .offset(0)
            .limit(limit)
            .list(rank1);
    }
}
