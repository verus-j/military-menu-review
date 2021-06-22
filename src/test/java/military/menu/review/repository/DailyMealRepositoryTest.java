package military.menu.review.repository;

import military.menu.review.domain.DailyMeal;
import military.menu.review.service.dto.DailyMealDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class DailyMealRepositoryTest {
    @Autowired DailyMealRepository dailyMealRepository;

    @BeforeEach
    void setUp() {
        int[] dates = {13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};

        for(int date : dates) {
            dailyMealRepository.save(DailyMeal.from(LocalDate.of(2021, 6, date)));
        }
    }

    @AfterEach
    void tearDown() {
        dailyMealRepository.deleteAll();
    }

    @Test
    public void shouldFindByDateBetween() {
        List<DailyMeal> dailyMeals = dailyMealRepository.findByDateBetween(
            LocalDate.of(2021, 6, 13), LocalDate.of(2021, 6, 18));

        int date = 13;

        for(DailyMeal dailyMeal : dailyMeals) {
            assertThat(dailyMeal.getDate(), is(LocalDate.of(2021, 6, date++)));
        }
    }
}
