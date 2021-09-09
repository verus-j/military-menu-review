package military.menu.review.domain.menu;

import military.menu.review.application.like.LikeService;
import military.menu.review.application.member.MemberService;
import military.menu.review.domain.member.Role;
import military.menu.review.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 메뉴 조회")
    public void selectByIdWithIsFalseLiked() throws Exception {
        MenuDto menuDto = dao.selectByIdWithIsLiked(menu.getId(), member.getId()).get();

        assertThat(menuDto.getId()).isEqualTo(menu.getId());
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

        MenuDto menuDto = dao.selectByIdWithIsLiked(menu.getId(), member.getId()).get();

        assertThat(menuDto.getId()).isEqualTo(menu.getId());
        assertThat(menuDto.getName()).isEqualTo(MENU_NAME);
        assertThat(menuDto.getKcal()).isEqualTo(KCAL);
        assertThat(menuDto.getLike()).isEqualTo(1L);
        assertThat(menuDto.isLiked()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 메뉴 조회")
    public void selectNotExistMenu() throws Exception {
        Optional<MenuDto> optional = dao.selectByIdWithIsLiked(menu.getId() + 10000, member.getId());
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("좋아요 여부와 함께 메뉴 페이지 조회")
    public void selectAllWithIsLiked() throws Exception {
        saveMenus();
        likeService.like(member, menu);
        flushAndClear();
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "menu_id"));

        List<MenuDto> menuDtoList = dao.selectAllWithIsLiked(pageable, member.getId());

        assertThat(menuDtoList).hasSize(3);
        assertThat(menuDtoList.get(0).getName()).isEqualTo(MENU_NAME);
        assertThat(menuDtoList.get(0).getKcal()).isEqualTo(KCAL);
        assertThat(menuDtoList.get(0).isLiked()).isTrue();
        assertThat(menuDtoList.get(1).getName()).isEqualTo("b");
        assertThat(menuDtoList.get(1).getKcal()).isEqualTo(2.0);
        assertThat(menuDtoList.get(1).isLiked()).isFalse();
        assertThat(menuDtoList.get(2).getName()).isEqualTo("c");
        assertThat(menuDtoList.get(2).getKcal()).isEqualTo(3.0);
        assertThat(menuDtoList.get(2).isLiked()).isFalse();
    }

    private void saveMenus() {
        List<Menu> menus = Arrays.asList(
                Menu.of("b", 2.0), Menu.of("c", 3.0),
                Menu.of("d", 4.0), Menu.of("e", 5.0), Menu.of("f", 6.0),
                Menu.of("g", 7.0), Menu.of("h", 8.0), Menu.of("i", 9.0),
                Menu.of("j", 10.0)
        );
        menuRepository.saveAll(menus);
        flushAndClear();
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }
}
