package military.menu.review.repository;

import military.menu.review.domain.Meal;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.MealType;
import military.menu.review.domain.Menu;
import military.menu.review.repository.menu.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@DataJpaTest
@ActiveProfiles("test")
public class MealMenuRepositoryTest {
    @Autowired
    private MealMenuRepository mealMenuRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void shouldFindByDateBetween() {
        Meal meal1 = Meal.of(MealType.BREAKFAST, LocalDate.of(2021, 6, 20));
        Meal meal2 = Meal.of(MealType.BREAKFAST, LocalDate.of(2021, 6, 21));
        Meal meal3 = Meal.of(MealType.LUNCH, LocalDate.of(2021, 6, 22));
        Meal meal4 = Meal.of(MealType.DINNER, LocalDate.of(2021, 6, 23));

        Menu menu1 = Menu.of("밥", 111.1);
        Menu menu2 = Menu.of("김치", 123.2);

        mealRepository.saveAll(Arrays.asList(meal1, meal2, meal3, meal4));
        menuRepository.saveAll(Arrays.asList(menu1, menu2));

        MealMenu mealMenu1 = MealMenu.of(meal1, menu1, 1);
        MealMenu mealMenu2 = MealMenu.of(meal1, menu2, 2);
        MealMenu mealMenu3 = MealMenu.of(meal2, menu1, 1);
        MealMenu mealMenu4 = MealMenu.of(meal3, menu1, 1);
        MealMenu mealMenu5 = MealMenu.of(meal3, menu2, 2);
        MealMenu mealMenu6 = MealMenu.of(meal4, menu1, 1);

        mealMenuRepository.saveAll(Arrays.asList(
                mealMenu1, mealMenu2, mealMenu3, mealMenu4, mealMenu5, mealMenu6
        ));

        em.flush();
        em.clear();

        List<MealMenu> expected = mealMenuRepository.findByDateBetween(
            LocalDate.of(2021, 6, 21), LocalDate.of(2021, 6, 22));

        assertThat(expected, is(Arrays.asList(mealMenu3, mealMenu4, mealMenu5)));
    }
}
