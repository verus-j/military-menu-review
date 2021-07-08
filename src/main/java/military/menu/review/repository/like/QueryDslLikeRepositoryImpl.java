package military.menu.review.repository.like;

import com.mysema.query.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static military.menu.review.domain.QLike.like;

@Repository
@RequiredArgsConstructor
public class QueryDslLikeRepositoryImpl implements QueryDslLikeRepository{
    private final EntityManager em;

    @Override
    public Like findByMemberAndMenuAndWeek(Member member, Menu menu, Week week) {
        JPAQuery query = new JPAQuery(em);

        return query.from(like)
            .join(like.member, QMember.member).fetch()
            .join(like.menu, QMenu.menu).fetch()
            .where(QMember.member.eq(member), QMenu.menu.eq(menu), like.week.eq(week))
            .singleResult(like);
    }
}
