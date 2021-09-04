package military.menu.review.infra.menu;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import military.menu.review.application.menu.MenuDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class QueryDslMenuRepositoryImpl {
    private final EntityManager em;

    public MenuDto findByIdWithMemberId(Long id, Long member) {
        JPAQuery query = new JPAQuery(em);

        return null;
    }
}
