package military.menu.review.mndapi.parser;

import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import military.menu.review.model.menu.MenuTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("국방부 API 데이터 파서 테스트")
public class MndApiDataParserTest {
    MndApiDataParser<String> basicParser;
    MenuListParser menuListParser;
    MenuTableParser menuTableParser;
    TotalCountParser totalCountParser;

    @BeforeEach
    void setUp() {
        basicParser = new MndApiDataParser<String>() {
            @Override
            public String parse(String json) {
                return json;
            }
        } ;
        menuListParser = new MenuListParser();
        menuTableParser = new MenuTableParser();
        totalCountParser = new TotalCountParser();
    }

    @Test
    @DisplayName("서비스 속성내의 데이터 분해")
    void shouldDestructByServiceColumn() {
        Map<String, Object> map = basicParser.destructToService("{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":23}}");
        Map<String, Object> expected = new HashMap<>();
        expected.put("list_total_count", 23);
        assertThat(map, is(expected));
    }

    @Test
    @DisplayName("서비스와 배열 속성 내의 데이터 분해")
    void shouldDestructByServiceAndListColumn() {
        List<Map<String, String>> list = basicParser.destructToMenuList("{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"row\":[{\"value\":\"1\"}, {\"value\":\"2\"}]}}");

        List<Map<String, String>> expected = new LinkedList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("value", "1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("value", "2");
        expected.add(map1);
        expected.add(map2);

        assertThat(list, is(expected));

    }

    @Test
    @DisplayName("JSON으로부터 전체 배열 길이 반환")
    public void shouldGetTotalCountFromJson() {
        int result = totalCountParser.parse("{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":23}}");
        assertThat(result, is(23));
    }

    @Test
    @DisplayName("JSON으로부터 메뉴 테이블 반환")
    void shouldGetMenuTableFromJson() {
        MenuTable table = menuTableParser.parse("{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"row\":[{\"dinr_cal\":\"363kcal\",\"lunc\":\"밥\",\"sum_cal\":\"2392.51kcal\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"2021-04-24\",\"lunc_cal\":\"363kcal\",\"brst\":\"밥\",\"dinr\":\"밥\",\"brst_cal\":\"363kcal\"}]}}");
        MenuTable expected = new MenuTable();
        DailyMenu dailyMenu = new DailyMenu(LocalDate.of(2021, 4, 24));
        dailyMenu.addBreakfastMenu(Menu.of("밥", 0.0));
        dailyMenu.addLunchMenu(Menu.of("밥", 0.0));
        dailyMenu.addDinnerMenu(Menu.of("밥", 0.0));
        expected.addDailyMenu(dailyMenu);
        assertThat(table, is(expected));
    }

    @Test
    @DisplayName("JSON으로부터 중복없는 메뉴 리스트 반환")
    public void shouldGetMenuListFromJson() {
        MenuList list = menuListParser.parse("{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"row\":[{\"dinr_cal\":\"363kcal\",\"lunc\":\"밥\",\"sum_cal\":\"2392.51kcal\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"2021-04-24\",\"lunc_cal\":\"363kcal\",\"brst\":\"밥\",\"dinr\":\"밥\",\"brst_cal\":\"363kcal\"}]}}");
        MenuList expected = new MenuList();
        expected.add(Menu.of("밥", 0.0));
        assertThat(list, is(expected));
    }
}
