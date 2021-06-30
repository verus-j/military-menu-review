package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.Meal;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.MealType;
import military.menu.review.domain.Menu;
import military.menu.review.repository.mealmenu.MealMenuRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.mnd.filter.MealInfo;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.exception.MenuNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static military.menu.review.domain.MealType.*;
import static military.menu.review.service.mnd.filter.impl.DTOUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SaveMealMenusFilterTest {
    private SaveMealMenusFilter filter;
    private MndFilterCache cache;
    private MealMenuRepository mealMenuRepository;

    @BeforeEach
    void setUp() {
        cache = mock(MndFilterCache.class);
        mealMenuRepository = mock(MealMenuRepository.class);
        filter = new SaveMealMenusFilter(mealMenuRepository);

        mockFindDtoList();
    }

    @Test
    public void shouldSaveMealMenuByCorrectOrder() {
        mockFindMealEntity();
        mockFindMenuEntity();

        filter.process(cache);

        verify(mealMenuRepository).save(MealMenu.of(Meal.of(BREAKFAST, DATE1), Menu.of("밥", 111.0), 1));
        verify(mealMenuRepository).save(MealMenu.of(Meal.of(LUNCH, DATE1), Menu.of("조밥", 111.0), 1));
        verify(mealMenuRepository).save(MealMenu.of(Meal.of(LUNCH, DATE1), Menu.of("김치", 111.0), 2));
        verify(mealMenuRepository).save(MealMenu.of(Meal.of(DINNER, DATE1), Menu.of("라면", 111.0), 1));
    }

    @Test
    public void shouldThrowMenuNotFoundException() {
        mockFindMealEntity();
        mockFindMenuEntity("라면");

        assertThrows(MenuNotFoundException.class, () -> filter.process(cache));
    }

    @Test
    public void shouldNotSaveNotExistMealInCache() {
        mockFindMealEntity(DINNER);
        mockFindMenuEntity("라면");

        filter.process(cache);

        verify(mealMenuRepository, times(0)).save(MealMenu.of(Meal.of(DINNER, DATE1), Menu.of("라면", 111.0), 1));
    }

    private void mockFindDtoList() {
        when(cache.findDtoList(DailyMealDTO.class)).thenReturn(Arrays.asList(dto1()));
    }

    private void mockFindMealEntity(MealType... exclude) {
        List<MealType> excludeList = Arrays.asList(exclude);

        for(MealType mealType : MealType.values()) {
            if(!excludeList.contains(mealType)) {
                when(cache.findEntity(Meal.class, new MealInfo(DATE1, mealType))).thenReturn(Meal.of(mealType, DATE1));
            }
        }
    }

    private void mockFindMenuEntity(String... exclude) {
        List<String> excludeList = Arrays.asList(exclude);
        String[] names = {"밥", "조밥", "김치", "라면"};

        for(String name : names) {
            if(!excludeList.contains(name)) {
                when(cache.findEntity(Menu.class, name)).thenReturn(Menu.of(name, 111.0));
            }
        }
    }
}
