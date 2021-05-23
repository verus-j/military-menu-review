package military.menu.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@SpringBootApplication
public class MilitaryMenuReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilitaryMenuReviewApplication.class, args);
	}

	@Bean
	public View view() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(true);
		return view;
	}
}
