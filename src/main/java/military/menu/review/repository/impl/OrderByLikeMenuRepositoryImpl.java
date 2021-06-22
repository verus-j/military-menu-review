package military.menu.review.repository.impl;

import military.menu.review.domain.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderByLikeMenuRepositoryImpl implements OrderByLikeMenuRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Menu> findByWeekOrderByLikeLimit(int limit) {
        List<Menu> menus = em.createQuery("select m from Menu m order by m.like desc", Menu.class)
            .setFirstResult(0).setMaxResults(limit).getResultList();
        return menus;
    }
}
