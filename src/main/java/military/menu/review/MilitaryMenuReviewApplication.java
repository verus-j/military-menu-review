package military.menu.review;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Like;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Role;
import military.menu.review.repository.MemberRepository;
import military.menu.review.service.LikeService;
import military.menu.review.service.MemberService;
import military.menu.review.service.MenuService;
import military.menu.review.service.mnd.MndService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
@RequiredArgsConstructor
public class MilitaryMenuReviewApplication {
	private final MemberService memberService;
	private final MenuService menuService;
	private final LikeService likeService;
	public static void main(String[] args) {
		SpringApplication.run(MilitaryMenuReviewApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(MndService service) {
		return new ApplicationRunner() {
			@Override
			@Transactional
			public void run(ApplicationArguments args) throws Exception {
				service.saveFromApi();
				Member member = Member.of("user", "123", "kim", "11-11111", Role.SOLDIER);
				Member normal = Member.of("normal", "123", "lee", "11-123211", Role.NORMAL);
				memberService.join(member);
				memberService.join(normal);
				Menu menu = menuService.findByName("밥");
				likeService.like(member, menu);
			}
		};
	}
}
