package military.menu.review;

import military.menu.review.domain.entity.DailyMeal;
import military.menu.review.mnd.service.MndService;
import military.menu.review.repository.DailyMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionManager;
import java.time.LocalDate;


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
