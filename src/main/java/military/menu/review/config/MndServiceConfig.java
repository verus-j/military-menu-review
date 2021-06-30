package military.menu.review.config;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.filter.impl.*;
import military.menu.review.repository.mealmenu.MealMenuRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.repository.menu.MenuRepository;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;
import military.menu.review.service.mnd.filter.MndSaveFilterBuilder;
import military.menu.review.service.mnd.filter.MndRestProcessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MndServiceConfig {
    private final MndApi api;
    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;
    private final MealMenuRepository mealMenuRepository;

    @Bean
    public MndRestProcessFilter headerChain() {
        return new MndSaveFilterBuilder(restMndData())
            .addChain(saveMenus())
            .addChain(saveMeals())
            .addChain(saveMealMenus())
            .build();
    }

    private MndRestProcessFilter restMndData() {
        return new RestMndDataFilter(api);
    }

    private MndSaveProcessFilter saveMenus() {
        return new SaveMenusFilter(menuRepository);
    }

    private MndSaveProcessFilter saveMeals() {
        return new SaveMealsFilter(mealRepository);
    }

    private MndSaveProcessFilter saveMealMenus() {
        return new SaveMealMenusFilter(mealMenuRepository);
    }
}
