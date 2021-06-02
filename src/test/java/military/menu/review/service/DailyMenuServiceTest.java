package military.menu.review.service;

import military.menu.review.mndapi.MndService;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.repository.DailyMenuRepository;
import military.menu.review.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DailyMenuServiceTest {
    @MockBean
    MenuRepository menuRepository;

    @MockBean
    DailyMenuRepository dailyMenuRepository;

    @MockBean
    MndService mndService;

    @Autowired
    DailyMenuService dailyMenuService;

    @Test
    void shouldSaveFromMnd() {
        DailyMenu dailyMenu = dailyMenu();
        when(mndService.findMenuList()).thenReturn(Arrays.asList(Menu.of("밥", 123.1)));
        when(menuRepository.findByName("밥")).thenReturn(null);
        when(mndService.findDailyMenuList()).thenReturn(Arrays.asList(dailyMenu));
        when(dailyMenuRepository.findByDate(LocalDate.of(2021, 5, 2))).thenReturn(dailyMenu());

        dailyMenuService.saveFromMnd(mndService);

        then(menuRepository).should().save(Menu.of("밥", 123.1));
        then(dailyMenuRepository).should(times(0)).save(dailyMenu);
    }

    private DailyMenu dailyMenu() {
        DailyMenu dailyMenu = new DailyMenu(LocalDate.of(2021, 5, 2));
        dailyMenu.addBreakfastMenu(Menu.of("밥", 123.1));
        dailyMenu.addDinnerMenu(Menu.of("밥", 123.1));
        dailyMenu.addLunchMenu(Menu.of("밥", 123.1));
        return dailyMenu;
    }

    @Test
    public void shouldFindByDateBetween() {
        LocalDate from = LocalDate.of(2021, 5, 2);
        LocalDate to = LocalDate.of(2021, 5, 6);
        dailyMenuService.findFromTo(from, to);

        then(dailyMenuRepository).should().findByDateBetweenOrderByDateAsc(from, to);
    }

}
