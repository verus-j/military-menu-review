package military.menu.review.repository;

import military.menu.review.domain.Member;
import military.menu.review.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DataJpaTest
@ActiveProfiles("test")
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void shouldFindByUsername() {
        Member member = Member.of("username", "password", "name","11-11111", Role.SOLDIER);
        memberRepository.save(member);
        em.flush();
        em.clear();

        Member expected = memberRepository.findByUsername("username");

        assertThat(expected.getUsername(), is(member.getUsername()));
        assertThat(expected.getId(), is(member.getId()));
    }

    @Test
    public void shouldNotFoundByUsername() {
        Member expected = memberRepository.findByUsername("username");
        assertThat(expected, is(nullValue()));
    }

}
