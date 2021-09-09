package military.menu.review.application.like;

import military.menu.review.application.like.exception.LikeIsAlreadyExistException;
import military.menu.review.application.like.exception.LikeIsNotExistException;
import military.menu.review.application.member.MemberService;
import military.menu.review.domain.member.MemberType;
import military.menu.review.domain.like.LikeRepository;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.like.Like;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LikeServiceTest {
    @Autowired
    LikeService likeService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴에 좋아요 추가")
    public void saveLike() throws Exception {
        Menu menu = Menu.of("밥", 100.0);
        Member member = Member.of("wilgur513", "pass", "wilgur", MemberType.SOLDIER);
        menuRepository.save(menu);
        memberService.join(member);

        Like like = likeService.like(member, menu);

        Optional<Like> likeOptional = likeRepository.findById(like.getId());
        Optional<Menu> menuOptional = menuRepository.findById(menu.getId());
        Like actualLike = likeOptional.get();
        Menu actualMenu = menuOptional.get();

        assertThat(actualLike.getMember()).isEqualTo(member);
        assertThat(actualLike.getMenu()).isEqualTo(menu);
        assertThat(actualMenu.getLike()).isEqualTo(1);
    }

    @Test
    @DisplayName("중복된 좋아요")
    public void duplicateLike() throws Exception {
        Menu menu = Menu.of("밥", 100.0);
        Member member = Member.of("wilgur513", "pass", "wilgur", MemberType.SOLDIER);
        menuRepository.save(menu);
        memberService.join(member);

        likeService.like(member, menu);

        assertThrows(LikeIsAlreadyExistException.class, () -> {
           likeService.like(member, menu);
        });
    }

    @Test
    @DisplayName("좋아요 누른 메뉴 취소")
    public void cancelLike() throws Exception {
        Menu menu = Menu.of("밥", 100.0);
        Member member = Member.of("wilgur513", "pass", "wilgur", MemberType.SOLDIER);
        menuRepository.save(menu);
        memberService.join(member);

        Like like = likeService.like(member, menu);
        likeService.cancel(member, menu);

        Optional<Like> likeOptional = likeRepository.findById(like.getId());
        assertThat(likeOptional.isPresent()).isFalse();
        assertThat(menu.getLike()).isEqualTo(0);
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 메뉴 좋아요 해제")
    public void cancelNotCreatedLike() throws Exception {
        Menu menu = Menu.of("밥", 100.0);
        Member member = Member.of("wilgur513", "pass", "wilgur", MemberType.SOLDIER);
        menuRepository.save(menu);
        memberService.join(member);

        assertThrows(LikeIsNotExistException.class, () -> {
            likeService.cancel(member, menu);
        });
    }
}
