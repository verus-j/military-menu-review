package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.DailyMeal;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;
import military.menu.review.service.mnd.filter.impl.exception.DailyMealNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveDailyMealsToCacheTest {
    private LocalDate date = LocalDate.of(2021, 3, 4);
    private DailyMealDTO dto = DailyMealDTO.of(date);
    private DailyMeal dailyMeal = DailyMeal.from(date);

    @Test
    public void shouldSaveToCache() {
        DailyMealRepository dailyMealRepository = mock(DailyMealRepository.class);
        when(dailyMealRepository.findByDate(date)).thenReturn(dailyMeal);

        MndSaveProcessFilter filter = new SaveDailyMealsToCache(dailyMealRepository);
        MndFilterCache cache = new MndFilterCache()
                .initDtoList(DailyMealDTO.class, Arrays.asList(dto))
                .initEntityMap(DailyMeal.class);

        filter.execute(cache);

        assertThat(cache.findEntity(DailyMeal.class, date), is(dailyMeal));
    }

    @Test
    public void shouldThrowExceptionWhenNotFoundDailyMealFromDB() {
        DailyMealRepository dailyMealRepository = mock(DailyMealRepository.class);
        when(dailyMealRepository.findByDate(date)).thenReturn(null);

        MndSaveProcessFilter filter = new SaveDailyMealsToCache(dailyMealRepository);
        MndFilterCache cache = new MndFilterCache()
                .initDtoList(DailyMealDTO.class, Arrays.asList(dto))
                .initEntityMap(DailyMeal.class);

        assertThrows(DailyMealNotFoundException.class, () -> {
            filter.execute(cache);
        });
    }
}
