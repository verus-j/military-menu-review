package military.menu.review;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.*;
import military.menu.review.repository.MealRepository;
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

import java.time.LocalDate;


@SpringBootApplication
@RequiredArgsConstructor
public class MilitaryMenuReviewApplication {
	private final MemberService memberService;
	private final MenuService menuService;
	private final LikeService likeService;
	private final MemberRepository memberRepository;
	private final MealRepository mealRepository;

	public static void main(String[] args) {
		SpringApplication.run(MilitaryMenuReviewApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(MndService service) {
		return new ApplicationRunner() {
			@Override
			@Transactional
			public void run(ApplicationArguments args) throws Exception {
//				service.saveFromApi();
//				if(memberRepository.findByUsername("user") == null) {
//					Member member = Member.of("user", "123", "kim", "11-11111", Role.SOLDIER);
//					Member normal = Member.of("normal", "123", "lee", "11-123211", Role.NORMAL);
//					Meal meal = mealRepository.findByDateAndType(LocalDate.of(2021, 6, 15), MealType.LUNCH);
//					memberService.join(member);
//					memberService.join(normal);
//					Menu menu = menuService.findByName("밥");
//					likeService.like(meal, menu, member);
//				}
			}
		};
	}
}
