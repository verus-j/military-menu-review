package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.menu.Menu;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.api.parser.DailyMealsParser;
import military.menu.review.service.mnd.api.parser.MenusParser;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.MndRestProcessFilter;

import java.util.List;

@RequiredArgsConstructor
public class RestMndDataFilter extends MndRestProcessFilter {
    private final MndApi api;

    @Override
    protected MndFilterCache initCache() {
        return new MndFilterCache()
                .initDtoList(DailyMealDTO.class, dailyMealDtos())
                .initDtoList(MenuDTO.class, menuDtos())
                .initEntityMap(Meal.class)
                .initEntityMap(Menu.class);
    }

    private List<DailyMealDTO> dailyMealDtos() {
        return api.parse(new DailyMealsParser());
    }

    private List<MenuDTO> menuDtos() {
        return api.parse(new MenusParser());
    }
}
