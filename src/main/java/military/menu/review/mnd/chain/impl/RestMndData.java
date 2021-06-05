package military.menu.review.mnd.chain.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.dto.MenuDTO;
import military.menu.review.mnd.api.MndApi;
import military.menu.review.mnd.api.parser.DailyMealsParser;
import military.menu.review.mnd.api.parser.MenusParser;
import military.menu.review.mnd.chain.MndSaveChainCache;
import military.menu.review.mnd.chain.MndSaveHeaderChain;

import java.util.List;

@RequiredArgsConstructor
public class RestMndData extends MndSaveHeaderChain {
    private final MndApi api;

    @Override
    public void execute() {
        next(new MndSaveChainCache(menuDtos(), dailyMealDtos()));
    }

    private List<DailyMealDTO> dailyMealDtos() {
        return api.parse(new DailyMealsParser());
    }

    private List<MenuDTO> menuDtos() {
        return api.parse(new MenusParser());
    }


}
