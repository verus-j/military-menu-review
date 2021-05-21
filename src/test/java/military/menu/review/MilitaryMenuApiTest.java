package military.menu.review;

import military.menu.review.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class MilitaryMenuApiTest {
	@Value("${mnd.baseUrl}")
	String baseUrl;

	@Autowired
	MilitaryMenuApi api;

	@MockBean
	RestTemplate template;

	@BeforeEach
	public void setup() {
		when(template.getForObject(baseUrl + "/1/1", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":3,\"row\":[{\"dinr_cal\":\"105.67kcal\",\"lunc\":\"김치찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"70.45kcal\",\"brst\":\"쇠고기무국(05)(16)\",\"dinr\":\"돼지고기고추장찌개(05)(06)(10)\",\"brst_cal\":\"45.12kcal\"}]}}"
		);
		when(template.getForObject(baseUrl + "/1/3", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":3,\"row\":[{\"dinr_cal\":\"105.67kcal\",\"lunc\":\"김치찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"70.45kcal\",\"brst\":\"쇠고기무국(05)(16)\",\"dinr\":\"돼지고기고추장찌개(05)(06)(10)\",\"brst_cal\":\"45.12kcal\"},{\"dinr_cal\":\"184.53kcal\",\"lunc\":\"느타리버섯볶음(05)(06)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"61.83kcal\",\"brst\":\"두부구이(05)(06)\",\"dinr\":\"오징어볶음(05)(06)(17)\",\"brst_cal\":\"123.97kcal\"},{\"dinr_cal\":\"95.56kcal\",\"lunc\":\"훈제삼겹살간장조림(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"309.66kcal\",\"brst\":\"부추나물\",\"dinr\":\"계란찜(01)(09)\",\"brst_cal\":\"19.45kcal\"}]}}"
		);
	}

	@Test
	public void shouldGetTotalMenuListCount() {
		api.setTemplate(template);
		int count = api.getTotalCount();

		assertThat(count, is(3));
	}

	@Test
	public void shouldGetTotalMenu() {
	 	api.setTemplate(template);
	 	List<Menu> menuList = api.getMenuList();

		assertThat(menuList, containsInAnyOrder(Menu.of("쇠고기무국", 45.12), Menu.of("김치찌개", 70.45), Menu.of("돼지고기고추장찌개", 105.67),
				Menu.of("두부구이", 123.97), Menu.of("느타리버섯볶음", 61.83), Menu.of("오징어볶음", 184.53),
				Menu.of("부추나물", 19.45), Menu.of("훈제삼겹살간장조림", 309.66), Menu.of("계란찜",95.56)));
	}
}
