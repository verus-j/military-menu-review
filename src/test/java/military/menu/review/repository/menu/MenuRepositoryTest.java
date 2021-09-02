package military.menu.review.repository.menu;

import military.menu.review.domain.*;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.repository.MemberRepository;
import military.menu.review.repository.MenuUtils;
import military.menu.review.repository.like.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

import static military.menu.review.repository.MenuUtils.createMenus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

@DataJpaTest
@ActiveProfiles("test")
public class MenuRepositoryTest {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private EntityManager em;

    private Member member;
    private Menu menu1;
    private Menu menu2;
    private Menu menu3;
    private Week week;

    @BeforeEach
    void setUp() {
        member = Member.of("username", "password", "name","11-11111", Role.SOLDIER);
        menu1 = Menu.of("김밥", 123.1);
        menu2 = Menu.of("라면", 234.2);
        menu3 = Menu.of("김치", 23.2);
        week = new Week(2021, 6, 2);
    }

    @Test
    public void shouldFindByName() {
        Menu menu = Menu.of("김밥", 123.1);
        menuRepository.save(menu);
        em.flush();
        em.clear();

        Menu expected = menuRepository.findByName("김밥");

        assertThat(expected, is(menu));
    }

    @Test
    public void shouldNotFoundByName() {
        Menu expected = menuRepository.findByName("김밥");
        assertThat(expected, is(nullValue()));
    }

    @Test
    public void shouldFindByMemberLikedDuringWeek() {
        memberRepository.save(member);
        menuRepository.saveAll(Arrays.asList(menu1, menu2, menu3));

        Like like1 = Like.of(member, menu1, week);
        Like like2 = Like.of(member, menu2, week);
        Like like3 = Like.of(member, menu3, new Week(2021, 7, 1));

        likeRepository.saveAll(Arrays.asList(like1, like2, like3));

        em.flush();
        em.clear();

        List<Menu> expected = menuRepository.findByMemberLikedDuringWeek(member, week);
        assertThat(expected, is(containsInAnyOrder(Arrays.asList(menu1, menu2).toArray())));
    }

    @Test
    public void shouldFindByMemberLikedDuringExactWeek() {
        memberRepository.save(member);
        menuRepository.saveAll(Arrays.asList(menu1, menu2));

        Like like1 = Like.of(member, menu1, week);
        Like like2 = Like.of(member, menu1, new Week(2021, 7, 1));

        likeRepository.saveAll(Arrays.asList(like1, like2));

        em.flush();
        em.clear();

        List<Menu> expected = menuRepository.findByMemberLikedDuringWeek(member, week);
        assertThat(expected, is(containsInAnyOrder(Arrays.asList(menu1).toArray())));
    }

    @Test
    public void shouldFindOrderByLikeLimit() {
        String[] names = {"김밥", "라면", "밥", "김치", "된장찌개"};
        int[] like = {10, 9, 7, 7, 5};

        List<Menu> menus = createMenus(names);
        MenuUtils.pressLike(menus, like);
        saveMenus(menus);

        em.flush();
        em.clear();

        List<Menu> expected = menuRepository.findOrderByLikeLimit(4);

        assertThat(expected, is(Arrays.asList(
            Menu.of("김밥", 111.1),
            Menu.of("라면", 111.1),
            Menu.of("김치", 111.1),
            Menu.of("밥", 111.1)))
        );
    }

    private void saveMenus(List<Menu> menus) {
        menus.stream().forEach(menuRepository::save);
    }
}
