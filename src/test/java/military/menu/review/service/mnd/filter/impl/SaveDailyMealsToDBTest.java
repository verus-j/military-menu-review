package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.DailyMeal;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveDailyMealsToDBTest {
    @Test
    public void shouldSaveToDB() {
        DailyMealRepository repository = mock(DailyMealRepository.class);
        MndSaveProcessFilter filter = new SaveDailyMealsToDB(repository);
        DailyMealDTO dto = DailyMealDTO.of(LocalDate.of(2021, 3, 4));
        filter.execute(
            new MndFilterCache()
                .initDtoList(DailyMealDTO.class,
                        Arrays.asList(dto)));

        verify(repository).save(any(DailyMeal.class));
    }

    @Test
    public void shouldNotSaveAlreadyExist() {
        DailyMealRepository repository = mock(DailyMealRepository.class);
        when(repository.findByDate(LocalDate.of(2021, 4, 4))).thenReturn(DailyMeal.from(LocalDate.of(2021, 4, 4)));
        DailyMealDTO dto = DailyMealDTO.of(LocalDate.of(2021, 4, 4));
        MndSaveProcessFilter filter = new SaveDailyMealsToDB(repository);
        filter.execute(
                new MndFilterCache()
                        .initDtoList(DailyMealDTO.class,
                                Arrays.asList(dto)));

        verify(repository, times(0)).save(any(DailyMeal.class));
    }
}
