package military.menu.review;

import military.menu.review.mndapi.MndService;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.Review;
import military.menu.review.model.menu.User;
import military.menu.review.repository.DailyMenuRepository;
import military.menu.review.repository.MenuRepository;
import military.menu.review.repository.ReviewRepository;
import military.menu.review.repository.UserRepository;
import military.menu.review.service.DailyMenuService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MilitaryMenuReviewApplication {
	@Autowired
	private DailyMenuRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	public static void main(String[] args) {
		SpringApplication.run(MilitaryMenuReviewApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner() {
		return (args) -> {
			DailyMenu dailyMenu = new DailyMenu(LocalDate.of(2021, 4, 3));
			dailyMenu.addBreakfastMenu(Menu.of("밥", 123.1));
			dailyMenu.addBreakfastMenu(Menu.of("김치", 13.1));
			dailyMenu.addLunchMenu(Menu.of("라면", 123.2));
			dailyMenu.addDinnerMenu(Menu.of("볶음밥", 232.2));
			dailyMenu.addDinnerMenu(Menu.of("된장찌개", 322.2));
			repository.save(dailyMenu);

			User user = new User("홍길동");
			userRepository.save(user);

			for(int i = 0; i < 7; i++) {
				Review review = new Review(user, "안녕하세요", LocalDate.of(2021, 4, 3 + i));
				review.setMenuList(dailyMenu.getBreakfast());
				reviewRepository.save(review);
			}
		};
	}
}
