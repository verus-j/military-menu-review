package military.menu.review.mnd;

import lombok.RequiredArgsConstructor;
import military.menu.review.mnd.api.MndApi;
import military.menu.review.mnd.chain.impl.*;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.repository.MealMenuRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.repository.MenuRepository;
import military.menu.review.mnd.chain.MndSaveBodyChain;
import military.menu.review.mnd.chain.MndSaveChainBuilder;
import military.menu.review.mnd.chain.MndSaveHeaderChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class MndServiceConfig {
    private final MndApi api;
    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;
    private final DailyMealRepository dailyMealRepository;
    private final MealMenuRepository mealMenuRepository;

    @Bean
    public MndSaveHeaderChain headerChain() {
        return new MndSaveChainBuilder(restMndData())
            .addChain(saveMenus())
            .addChain(saveDailyMeals())
            .addChain(saveMeals())
            .addChain(saveMealMenus())
            .build();
    }

    private MndSaveHeaderChain restMndData() {
        return new RestMndData(api);
    }

    private MndSaveBodyChain saveMenus() {
        return new SaveMenus(menuRepository);
    }

    private MndSaveBodyChain saveDailyMeals() {
        return new SaveDailyMeals(dailyMealRepository);
    }

    private MndSaveBodyChain saveMeals() {
        return new SaveMeals(mealRepository);
    }

    private MndSaveBodyChain saveMealMenus() {
        return new SaveMealMenus(mealMenuRepository);
    }
}
