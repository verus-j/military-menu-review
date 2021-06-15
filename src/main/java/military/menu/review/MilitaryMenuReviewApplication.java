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
	public static void main(String[] args) {
		SpringApplication.run(MilitaryMenuReviewApplication.class, args);
	}
}
