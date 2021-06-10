package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.domain.DailyMeal;
import military.menu.review.domain.Meal;
import military.menu.review.domain.Menu;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.api.parser.DailyMealsParser;
import military.menu.review.service.mnd.api.parser.MenusParser;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.MndRestProcessFilter;

import java.util.List;

@RequiredArgsConstructor
public class RestMndData extends MndRestProcessFilter {
    private final MndApi api;

    @Override
    public void execute() {
        MndFilterCache cache = new MndFilterCache()
                .initDtoList(DailyMealDTO.class, dailyMealDtos())
                .initDtoList(MenuDTO.class, menuDtos())
                .initEntityMap(DailyMeal.class)
                .initEntityMap(Meal.class)
                .initEntityMap(Menu.class);

        next(cache);
    }

    private List<DailyMealDTO> dailyMealDtos() {
        return api.parse(new DailyMealsParser());
    }

    private List<MenuDTO> menuDtos() {
        return api.parse(new MenusParser());
    }
}
