package military.menu.review.service.mnd.filter.impl;

import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.api.parser.DailyMealsParser;
import military.menu.review.service.mnd.api.parser.MenusParser;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.MndRestProcessFilter;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class RestMndDataFilterTest {
    private MndApi api;
    private MndRestProcessFilter filter;

    @BeforeEach
    void setUp() {
        api = mock(MndApi.class);
        filter = new RestMndDataFilter(api);
    }

    @Test
    public void shouldRestMndData() {
        filter.execute();
        verify(api).parse(any(MenusParser.class));
        verify(api).parse(any(DailyMealsParser.class));
    }

    @Test
    public void shouldInitCache() {
        List<MenuDTO> menus = Arrays.asList(MenuDTO.of("밥", 100.0));
        List<DailyMealDTO> dailyMeals = Arrays.asList(DailyMealDTO.of(LocalDate.of(2021, 2, 3)));

        when(api.parse(any(MenusParser.class))).thenReturn(menus);
        when(api.parse(any(DailyMealsParser.class))).thenReturn(dailyMeals);

        MndSaveProcessFilter next = new MndSaveProcessFilter() {
            @Override
            protected void process(MndFilterCache cache) {
                assertThat(cache.findDtoList(DailyMealDTO.class), is(dailyMeals));
                assertThat(cache.findDtoList(MenuDTO.class), is(menus));
            }
        };

        filter.setNext(next);
        filter.execute();
    }
}
