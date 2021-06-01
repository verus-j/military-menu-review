package military.menu.review.repository;

import military.menu.review.mndapi.MndApi;
import military.menu.review.mndapi.parser.DailyMenuListParser;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MndDailyMenuRepositoryTest {
    @MockBean
    MndApi api;

    @Resource(name="mndDailyMenuRepository")
    DailyMenuRepository repository;

    @BeforeEach
    void setUp() {
        DailyMenu menu = new DailyMenu(LocalDate.of(2021, 3, 4));
        menu.addBreakfastMenu(Menu.of("식빵", 101.1));
        menu.addLunchMenu(Menu.of("밥", 101.1));
        menu.addDinnerMenu(Menu.of("김치", 123.2));

        when(api.parse(any(DailyMenuListParser.class)))
            .thenReturn(Arrays.asList(menu));
    }

    @Test
    public void shouldFindAll() {
        List<DailyMenu> dailyMenuList = repository.findAll();
        DailyMenu dailyMenu = dailyMenuList.get(0);

        assertThat(dailyMenu.getBreakfast(), is(MenuList.asList(Menu.of("식빵", 101.1))));
        assertThat(dailyMenu.getLunch(), is(MenuList.asList(Menu.of("밥", 101.1))));
        assertThat(dailyMenu.getDinner(), is(MenuList.asList(Menu.of("김치", 123.2))));
    }

    @Test
    void shouldThrowUnsupportedException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            repository.insert();
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.insertAll(Arrays.asList(new DailyMenu(LocalDate.now())));
        });
    }
}
