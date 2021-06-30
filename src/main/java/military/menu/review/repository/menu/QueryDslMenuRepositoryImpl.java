package military.menu.review.repository.menu;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static military.menu.review.domain.QLike.like;
import static military.menu.review.domain.QMenu.menu;

@Repository
@RequiredArgsConstructor
public class QueryDslMenuRepositoryImpl implements QueryDslMenuRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Menu> findByMemberLikedDuringWeek(Member member, Week week) {
        JPAQuery query = new JPAQuery(em);
        return query.distinct().from(like)
            .join(like.menu, menu)
            .join(like.member, QMember.member)
            .where(like.member.eq(member).and(like.week.eq(week)))
            .list(menu);
    }

    @Override
    public List<Menu> findOrderByLikeLimit(int limit) {
        JPAQuery query = new JPAQuery(em);
        return query.from(menu)
            .orderBy(menu.like.desc())
            .orderBy(menu.name.asc())
            .offset(0)
            .limit(limit)
            .list(menu);
    }
}
