package military.menu.review.repository;

import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class JpaDailyMenuRepositoryTest {
    @Autowired
    DailyMenuRepository repository;

    @Test
    public void shouldFindByDate() {
        DailyMenu actual = repository.save(dailyMenu());
        DailyMenu expected = repository.findByDate(LocalDate.of(2021, 3, 5));

        assertThat(expected, is(actual));
    }

    @Test
    public void shouldFindByDateBetween() {
        repository.save(new DailyMenu(LocalDate.of(2021, 5, 1)));
        DailyMenu d2 = repository.save(new DailyMenu(LocalDate.of(2021, 5, 2)));
        DailyMenu d3 = repository.save(new DailyMenu(LocalDate.of(2021, 5, 3)));
        DailyMenu d4 = repository.save(new DailyMenu(LocalDate.of(2021, 5, 4)));
        repository.save(new DailyMenu(LocalDate.of(2021, 5, 5)));

        List<DailyMenu> list = repository.findByDateBetweenOrderByDateAsc(LocalDate.of(2021, 5, 2), LocalDate.of(2021, 5, 4));
        assertThat(list, is(Arrays.asList(d2, d3, d4)));
    }

    private DailyMenu dailyMenu() {
        DailyMenu dailyMenu = new DailyMenu(LocalDate.of(2021, 3, 5));
        dailyMenu.addBreakfastMenu(Menu.of("밥", 101.1));
        dailyMenu.addBreakfastMenu(Menu.of("김치", 123.1));
        dailyMenu.addLunchMenu(Menu.of("라면", 232.1));
        dailyMenu.addLunchMenu(Menu.of("깍두기", 111.1));
        dailyMenu.addDinnerMenu(Menu.of("볶음밥", 123.1));
        dailyMenu.addDinnerMenu(Menu.of("김치국", 123.1));
        return dailyMenu;
    }
}
