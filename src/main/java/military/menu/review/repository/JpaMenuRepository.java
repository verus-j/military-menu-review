package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaMenuRepository implements MenuRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Menu> findAll() {
        return em.createQuery("SELECT m FROM Menu m").getResultList();
    }

    @Override
    public Menu findByName(String name) {
        return em.createQuery("SELECT m FROM Menu m WHERE m.name = :name", Menu.class)
            .setParameter("name", name)
            .getSingleResult();
    }

    @Override
    public void insert(Menu menu) {
        em.persist(menu);
    }

    @Override
    public void insertAll(List<Menu> list) {
        list.forEach(em::persist);
    }
}
