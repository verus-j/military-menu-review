package military.menu.review.domain.menu;

import military.menu.review.application.like.LikeService;
import military.menu.review.application.member.MemberService;
import military.menu.review.application.menu.MenuDto;
import military.menu.review.domain.member.Role;
import military.menu.review.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MenuDaoTest {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    LikeService likeService;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;
    @Autowired
    MenuDao dao;

    static String MENU_NAME = "밥";
    static double KCAL = 100.1;
    Menu menu;
    Member member;

    @BeforeEach
    void setUp() {
        menu = Menu.of(MENU_NAME, KCAL);
        member = Member.of("wilgur513", "pass", "", "", Role.NORMAL);

        menuRepository.save(menu);
        memberService.join(member);
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 메뉴 조회")
    public void selectByIdWithIsFalseLiked() throws Exception {
        MenuDto menuDto = dao.selectByIdWithIsLiked(menu.getId(), member.getId());

        assertThat(menuDto.getId()).isNotNull();
        assertThat(menuDto.getName()).isEqualTo(MENU_NAME);
        assertThat(menuDto.getKcal()).isEqualTo(KCAL);
        assertThat(menuDto.getLike()).isEqualTo(0);
        assertThat(menuDto.isLiked()).isFalse();
    }

    @Test
    @DisplayName("좋아요를 누른 메뉴 조회")
    public void selectByIdWithIsTrueLiked() throws Exception {
        likeService.like(member, menu);
        em.flush();
        em.clear();
        
        MenuDto menuDto = dao.selectByIdWithIsLiked(menu.getId(), member.getId());

        assertThat(menuDto.getId()).isNotNull();
        assertThat(menuDto.getName()).isEqualTo(MENU_NAME);
        assertThat(menuDto.getKcal()).isEqualTo(KCAL);
        assertThat(menuDto.getLike()).isEqualTo(1L);
        assertThat(menuDto.isLiked()).isTrue();
    }
}
