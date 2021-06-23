package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.Meal;
import military.menu.review.repository.MealRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.service.mnd.filter.MndFilterCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
public class SaveMealsFilterTest {
    private static final LocalDate date1 = LocalDate.of(2021, 6, 11);
    private static final LocalDate date2 = LocalDate.of(2021, 6, 12);

    private MndFilterCache cache;
    private SaveMealsFilter filter;

    @Autowired
    private MealRepository mealRepository;

    @BeforeEach
    void setUp() {
        cache = mock(MndFilterCache.class);
        when(cache.findDtoList(DailyMealDTO.class)).thenReturn(dailyMealDtos());
        filter = new SaveMealsFilter(mealRepository);
    }

    @Test
    public void shouldSaveOnlyMealEntityToDB() {
        when(cache.findDtoList(DailyMealDTO.class)).thenReturn(Arrays.asList(dto1()));
        filter.process(cache);
        List<Meal> meals = mealRepository.findAll();
        assertThat(meals.size(), is(3));

    }

    @Test
    void shouldSaveNotEmptyMealToDB() {
        filter.process(cache);
        List<Meal> meals = mealRepository.findAll();
        assertThat(meals.size(), is(5));
    }

    private List<DailyMealDTO> dailyMealDtos() {
        return Arrays.asList(dto1(), dto2());
    }

    private DailyMealDTO dto1() {
        DailyMealDTO dto = DailyMealDTO.of(date1);
        dto.addBreakfastMenu(MenuDTO.of("밥", 111));
        dto.addLunchMenu(MenuDTO.of("조밥", 111));
        dto.addLunchMenu(MenuDTO.of("김치", 111));
        dto.addDinnerMenu(MenuDTO.of("라면", 111));
        return dto;
    }

    private DailyMealDTO dto2() {
        DailyMealDTO dto = DailyMealDTO.of(date2);
        dto.addBreakfastMenu(MenuDTO.of("카레", 111));
        dto.addLunchMenu(MenuDTO.of("볶음밥", 111));
        return dto;
    }
}
