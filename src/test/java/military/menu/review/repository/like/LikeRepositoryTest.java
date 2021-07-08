package military.menu.review.repository.like;

import military.menu.review.domain.*;
import military.menu.review.repository.MemberRepository;
import military.menu.review.repository.like.LikeRepository;
import military.menu.review.repository.menu.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DataJpaTest
@ActiveProfiles("test")
public class LikeRepositoryTest {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void shouldFindByMemberAndMenuAndWeek() {
        Member member = Member.of("username", "password", "name", "11-1111", Role.SOLDIER);
        memberRepository.save(member);

        Menu menu = Menu.of("김밥", 111.1);
        menuRepository.save(menu);

        Like like = Like.of(member, menu, new Week(2021, 6, 3));
        likeRepository.save(like);

        em.flush();
        em.clear();

        Like expected = likeRepository.findByMemberAndMenuAndWeek(member, menu, new Week(2021, 6, 3));

        assertThat(expected.getMember().getUsername(), is(member.getUsername()));
        assertThat(expected.getMenu(), is(menu));
        assertThat(expected.getWeek(), is(new Week(2021, 6, 3)));
    }

}
