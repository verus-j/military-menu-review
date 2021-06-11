package military.menu.review.service.mnd.api;

import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.api.parser.DailyMealsParser;
import military.menu.review.service.mnd.api.parser.MenusParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("국방부 메뉴 데이터 서비스 테스트")
class MndApiTest {
	String baseUrl = "baseUrl";
	RestTemplate template;
	MndApi api;

	@BeforeEach
	public void setup() {
		template = mock(RestTemplate.class);
		when(template.getForObject(baseUrl + "/1/1", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":3,\"row\":[{\"dinr_cal\":\"105.67kcal\",\"lunc\":\"김치찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"70.45kcal\",\"brst\":\"쇠고기무국(05)(16)\",\"dinr\":\"돼지고기고추장찌개(05)(06)(10)\",\"brst_cal\":\"45.12kcal\"}]}}"
		);
		when(template.getForObject(baseUrl + "/1/3", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":3,\"row\":[{\"dinr_cal\":\"105.67kcal\",\"lunc\":\"김치찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"70.45kcal\",\"brst\":\"쇠고기무국(05)(16)\",\"dinr\":\"돼지고기고추장찌개(05)(06)(10)\",\"brst_cal\":\"45.12kcal\"},{\"dinr_cal\":\"184.53kcal\",\"lunc\":\"느타리버섯볶음(05)(06)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"61.83kcal\",\"brst\":\"두부구이(05)(06)\",\"dinr\":\"오징어볶음(05)(06)(17)\",\"brst_cal\":\"123.97kcal\"},{\"dinr_cal\":\"95.56kcal\",\"lunc\":\"훈제삼겹살간장조림(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"309.66kcal\",\"brst\":\"부추나물\",\"dinr\":\"계란찜(01)(09)\",\"brst_cal\":\"19.45kcal\"}]}}"
		);

		api = new MndApi();
		api.setTemplate(template);
		api.setBaseUrl(baseUrl);
	}

	@Test
	@DisplayName("전체 메뉴 리스트 반환")
	public void shouldGetTotalMenu() {
	 	List<MenuDTO> menuList = menuList();
		assertThat(menuList, is(Arrays.asList(MenuDTO.of("쇠고기무국", 45.12), MenuDTO.of("김치찌개", 70.45), MenuDTO.of("돼지고기고추장찌개", 105.67),
				MenuDTO.of("두부구이", 123.97), MenuDTO.of("느타리버섯볶음", 61.83), MenuDTO.of("오징어볶음", 184.53),
				MenuDTO.of("부추나물", 19.45), MenuDTO.of("훈제삼겹살간장조림", 309.66), MenuDTO.of("계란찜",95.56))));
	}

	@Test
	@DisplayName("중복되지 않은 전체 메뉴 리스트 반환")
	public void shouldGetDistinctTotalMenu() {
		when(template.getForObject(baseUrl + "/1/3", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":3,\"row\":[{\"dinr_cal\":\"105.67kcal\",\"lunc\":\"김치찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"70.45kcal\",\"brst\":\"쇠고기무국(05)(16)\",\"dinr\":\"돼지고기고추장찌개(05)(06)(10)\",\"brst_cal\":\"45.12kcal\"},{\"dinr_cal\":\"184.53kcal\",\"lunc\":\"김치찌개(05)(06)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"61.83kcal\",\"brst\":\"김치찌개(05)(06)\",\"dinr\":\"오징어볶음(05)(06)(17)\",\"brst_cal\":\"123.97kcal\"},{\"dinr_cal\":\"95.56kcal\",\"lunc\":\"훈제삼겹살간장조림(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"309.66kcal\",\"brst\":\"부추나물\",\"dinr\":\"계란찜(01)(09)\",\"brst_cal\":\"19.45kcal\"}]}}"
		);
		List<MenuDTO> menuList = menuList();
		assertThat(menuList, is(Arrays.asList(MenuDTO.of("쇠고기무국", 45.12), MenuDTO.of("김치찌개", 70.45), MenuDTO.of("돼지고기고추장찌개", 105.67),
				MenuDTO.of("오징어볶음", 184.53), MenuDTO.of("부추나물", 19.45), MenuDTO.of("훈제삼겹살간장조림", 309.66), MenuDTO.of("계란찜",95.56))));
	}

	@Test
	@DisplayName("메뉴 데이터 중 빈 데이터는 리스트에서 제거 후 반환")
	void shouldRemoveEmptyMenu() {
		when(template.getForObject(baseUrl + "/1/1", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":7}}"
		);
		when(template.getForObject(baseUrl + "/1/7", String.class)).thenReturn(
				"{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":7,\"row\":[{\"dinr_cal\":\"105.67kcal\",\"lunc\":\"김치찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"70.45kcal\",\"brst\":\"쇠고기무국(05)(16)\",\"dinr\":\"돼지고기고추장찌개(05)(06)(10)\",\"brst_cal\":\"45.12kcal\"},{\"dinr_cal\":\"184.53kcal\",\"lunc\":\"느타리버섯볶음(05)(06)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"61.83kcal\",\"brst\":\"두부구이(05)(06)\",\"dinr\":\"오징어볶음(05)(06)(17)\",\"brst_cal\":\"123.97kcal\"},{\"dinr_cal\":\"95.56kcal\",\"lunc\":\"훈제삼겹살간장조림(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"309.66kcal\",\"brst\":\"부추나물\",\"dinr\":\"계란찜(01)(09)\",\"brst_cal\":\"19.45kcal\"},{\"dinr_cal\":\"28.75kcal\",\"lunc\":\"배추김치(3~4월)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"10.75kcal\",\"brst\":\"배추김치(3~4월)\",\"dinr\":\"깍두기(임가공)\",\"brst_cal\":\"10.75kcal\"},{\"dinr_cal\":\"\",\"lunc\":\"\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"\",\"brst\":\"농후 발효유(02)\",\"dinr\":\"\",\"brst_cal\":\"96kcal\"},{\"dinr_cal\":\"\",\"lunc\":\"\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"\",\"brst\":\"우유(백색우유(200ML,연간))(02)\",\"dinr\":\"\",\"brst_cal\":\"122kcal\"},{\"dinr_cal\":\"\",\"lunc\":\"\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"\",\"lunc_cal\":\"\",\"brst\":\"용기면,쌀국수,얼큰한맛,92g(다수공급자)\",\"dinr\":\"\",\"brst_cal\":\"\"}]}}"
		);
		List<MenuDTO> menuList = menuList();
		assertThat(menuList, is(Arrays.asList(MenuDTO.of("쇠고기무국", 45.12), MenuDTO.of("김치찌개", 70.45), MenuDTO.of("돼지고기고추장찌개", 105.67),
				MenuDTO.of("두부구이", 123.97), MenuDTO.of("느타리버섯볶음", 61.83), MenuDTO.of("오징어볶음", 184.53),
				MenuDTO.of("부추나물", 19.45), MenuDTO.of("훈제삼겹살간장조림", 309.66), MenuDTO.of("계란찜",95.56),
				MenuDTO.of("배추김치", 0.0), MenuDTO.of("깍두기", 0.0), MenuDTO.of("농후 발효유", 0.0),
				MenuDTO.of("우유", 0.0), MenuDTO.of("용기면,쌀국수,얼큰한맛,92g", 0.0))));
	}

	@Test
	@DisplayName("메뉴들의 칼로리에는 값이 존재")
	public void shouldMenuCalorieIsNotZero() {
		List<MenuDTO> menuList = menuList();
		assertThat(menuList.get(0).getKcal(), is(not(0.0)));
		assertThat(menuList.get(1).getKcal(), is(not(0.0)));
		assertThat(menuList.get(2).getKcal(), is(not(0.0)));
	}

	private String url(int start, int end) {
		return String.format("%s/%d/%d", baseUrl, start, end);
	}

	private List<MenuDTO> menuList() {
		return api.parse(new MenusParser());
	}

	private List<DailyMealDTO> menuTable() {
		return api.parse(new DailyMealsParser());
	}

	private MenuDTO menu(String name) {
		return MenuDTO.of(name, 0.0);
	}
}
