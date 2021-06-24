package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.Meal;
import military.menu.review.domain.MealType;
import military.menu.review.repository.MealRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.mnd.filter.MealInfo;
import military.menu.review.service.mnd.filter.MndFilterCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static military.menu.review.service.mnd.filter.impl.DTOUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
public class SaveMealsFilterTest {
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

    @Test
    public void shouldSaveOnlyMealEntityToCache() {
        when(cache.findDtoList(DailyMealDTO.class)).thenReturn(Arrays.asList(dto1()));
        filter.process(cache);

        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.BREAKFAST)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.LUNCH)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.DINNER)), any(Meal.class));
    }

    @Test
    public void shouldSaveNotEmptyMealToCache() {
        filter.process(cache);

        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.BREAKFAST)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.LUNCH)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.DINNER)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE2, MealType.BREAKFAST)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE2, MealType.LUNCH)), any(Meal.class));
    }

    @Test
    public void shouldNotSaveExistMeal() {
        mealRepository.save(Meal.of(MealType.BREAKFAST, DATE1));
        when(cache.findDtoList(DailyMealDTO.class)).thenReturn(Arrays.asList(dto1()));

        filter.process(cache);

        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.LUNCH)), any(Meal.class));
        verify(cache).putEntity(eq(new MealInfo(DATE1, MealType.DINNER)), any(Meal.class));
    }
}
