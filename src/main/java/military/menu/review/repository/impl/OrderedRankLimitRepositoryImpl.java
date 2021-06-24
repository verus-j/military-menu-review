package military.menu.review.repository.impl;

import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderedRankLimitRepositoryImpl implements OrderedRankLimitRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Rank> findByWeekOrderByRankLimit(Week week, int limit) {
        return em.createQuery("select r from Rank r join fetch r.menu where r.week=:week order by r.rank", Rank.class)
            .setParameter("week", week)
            .setFirstResult(0)
            .setMaxResults(limit)
            .getResultList();
    }
}
