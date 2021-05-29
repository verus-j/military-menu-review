package military.menu.review.repository;

import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@SpringBootTest
public class DailyMenuRepositoryTest {
    @Autowired
    DailyMenuRepository repository;

    @Autowired
    MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        menuRepository.deleteAll();

        menuRepository.insert(Menu.of("아침", 10.0, 100));
        menuRepository.insert(Menu.of("점심", 10.0, 100));
        menuRepository.insert(Menu.of("저녁", 10.0, 100));
    }

    @Test
    @Transactional
    void shouldSaveDailyMenu() {
        repository.insert(dailyMenu());
        Optional<DailyMenu> dailyMenuOptional = repository.find(dailyMenu().getDate());
        dailyMenuOptional.ifPresent(this::assertDailyMenu);
    }

    private DailyMenu dailyMenu() {
        DailyMenu dailyMenu = new DailyMenu(LocalDate.of(2012, 2, 3));
        dailyMenu.addBreakfastMenu(Menu.of("아침", 10.0, 100));
        dailyMenu.addLunchMenu(Menu.of("점심", 10.0, 100));
        dailyMenu.addDinnerMenu(Menu.of("저녁", 10.0, 100));
        return dailyMenu;
    }

    private void assertDailyMenu(DailyMenu dailyMenu) {
        assertThat(dailyMenu.getDate(), is(LocalDate.of(2012, 2, 3)));
        assertThat(dailyMenu.getBreakfast().getList(), is(Arrays.asList(Menu.of("아침", 10.0, 100))));
        assertThat(dailyMenu.getLunch().getList(), is(Arrays.asList(Menu.of("점심", 10.0, 100))));
        assertThat(dailyMenu.getDinner().getList(), is(Arrays.asList(Menu.of("저녁", 10.0, 100))));
    }
}
