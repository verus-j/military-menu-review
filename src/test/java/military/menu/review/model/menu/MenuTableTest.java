package military.menu.review.model.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("메뉴 테이블 테스트")
public class MenuTableTest {
    @Test
    @DisplayName("메뉴 테이블에 저장된 메뉴는 찾을 수 있다.")
    public void shouldFindRegisteredDiaryMenu() {
        MenuTable table = new MenuTable();
        table.addDailyMenu(dailyMenu());
        assertThat(table.getDailyMenu(2021, 4, 23), is(dailyMenu()));
    }

    @Test
    @DisplayName("저장되지 않은 일일 메뉴를 찾으면 빈 일일메뉴를 반환하다.")
    public void shouldReturnEmptyMenuListWhenFindNotRegisteredDiaryMenu() {
        MenuTable table = new MenuTable();
        assertThat(table.getDailyMenu(2021, 4, 23), is(new DailyMenu(LocalDate.of(2021, 4, 23))));
    }

    private DailyMenu dailyMenu() {
        DailyMenu menu = new DailyMenu(LocalDate.of(2021, 4, 23));
        menu.addDinnerMenu(Menu.of("저녁", 0.0));
        menu.addLunchMenu(Menu.of("점심", 0.0));
        menu.addBreakfastMenu(Menu.of("아침", 0.0));
        return menu;
    }
}
