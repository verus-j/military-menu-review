package military.menu.review.model.menu;

import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DailyMenuTest {
    DailyMenu dailyMenu;

    @BeforeEach
    void setUp() {
        dailyMenu = new DailyMenu(LocalDate.of(2021, 3, 23));
    }

    @Test
    public void shouldHasDate() {
        assertThat(dailyMenu.getDate(), is(LocalDate.of(2021, 3, 23)));
    }

    @Test
    public void shouldAddMenuByType() {
        dailyMenu.addBreakfastMenu(new Menu("라면", 123.1));
        dailyMenu.addLunchMenu(new Menu("밥", 123.1));
        dailyMenu.addDinnerMenu(new Menu("김치", 123.1));

        assertThat(dailyMenu.getLunch(), is(MenuList.asList(Menu.of("밥", 123.1))));
        assertThat(dailyMenu.getDinner(), is(MenuList.asList(Menu.of("김치", 123.1))));
        assertThat(dailyMenu.getBreakfast(), is(MenuList.asList(Menu.of("라면", 123.1))));
    }
}
