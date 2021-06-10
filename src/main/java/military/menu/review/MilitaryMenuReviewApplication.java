package military.menu.review;

import military.menu.review.service.mnd.MndService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MilitaryMenuReviewApplication {
	public static void main(String[] args) {
		SpringApplication.run(MilitaryMenuReviewApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(MndService service) {
		return (args) -> {
			service.saveFromApi();
		};
	}
}
