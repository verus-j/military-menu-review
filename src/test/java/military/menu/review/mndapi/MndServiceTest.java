package military.menu.review.mndapi;

import military.menu.review.mndapi.parser.DailyMenuListParser;
import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import military.menu.review.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MndServiceTest {
    @MockBean
    MndApi api;

    @Autowired
    MndService service;

    @BeforeEach
    void setUp() {
        DailyMenu menu = new DailyMenu(LocalDate.of(2021, 3, 4));
        menu.addBreakfastMenu(Menu.of("식빵", 101.1));
        menu.addLunchMenu(Menu.of("밥", 101.1));
        menu.addDinnerMenu(Menu.of("김치", 123.2));

        when(api.parse(any(MenuListParser.class)))
                .thenReturn(Arrays.asList(Menu.of("밥", 0.0), Menu.of("김치", 0.0)));

        when(api.parse(any(DailyMenuListParser.class)))
                .thenReturn(Arrays.asList(menu));
    }

    @Test
    public void shouldFindMenuList() {
        List<Menu> menuList = service.findMenuList();
        assertThat(menuList, is(Arrays.asList(Menu.of("밥", 0.0), Menu.of("김치", 0.0))));
    }
    
    @Test
    public void shouldFindDailyMenuList() {
        List<DailyMenu> dailyMenuList = service.findDailyMenuList();
        DailyMenu dailyMenu = dailyMenuList.get(0);

        assertThat(dailyMenu.getBreakfast(), is(MenuList.asList(Menu.of("식빵", 101.1))));
        assertThat(dailyMenu.getLunch(), is(MenuList.asList(Menu.of("밥", 101.1))));
        assertThat(dailyMenu.getDinner(), is(MenuList.asList(Menu.of("김치", 123.2))));
    }

}
