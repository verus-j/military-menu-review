package military.menu.review.repository;

import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.MealType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


@DataJpaTest
@ActiveProfiles("test")
public class MealRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired MealRepository mealRepository;

    @Test
    public void shouldFindById() {
        Meal meal = saveMeal();

        Optional<Meal> expected = mealRepository.findById(meal.getId());

        assertThat(expected.isPresent(), is(true));
        assertThat(expected.get(), is(meal));
    }

    @Test
    public void shouldNotFoundById() {
        Optional<Meal> expected = mealRepository.findById(1L);
        assertThat(expected.isPresent(), is(false));
    }

    @Test
    public void shouldFindByDateAndType() {
        Meal meal = saveMeal();
        Meal expected = mealRepository.findByDateAndType(meal.getDate(), meal.getType());
        assertThat(expected, is(meal));
    }

    @Test
    public void shouldNotFoundByDateAndType() {
         Meal expected = mealRepository.findByDateAndType(LocalDate.of(2021, 6, 21), MealType.BREAKFAST);
         assertThat(expected, is(nullValue()));
    }

    private Meal saveMeal() {
        Meal meal = Meal.of(MealType.BREAKFAST, LocalDate.of(2021, 6, 21));
        mealRepository.save(meal);
        em.flush();
        em.clear();
        return meal;
    }
}
