package military.menu.review.mnd;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.meal.*;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuRepository;
import military.menu.review.mnd.api.MndApi;
import military.menu.review.mnd.api.dto.MndMealDTO;
import military.menu.review.mnd.api.dto.MndMenuDTO;
import military.menu.review.mnd.api.parser.MndDataParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class MndSaveRunner implements ApplicationRunner {
    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;
    private final SelectedMenuRepository selectedMenuRepository;
    private final MndApi api;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        List<MndMealDTO> dataList = api.parse(new MndDataParser());

        for(MndMealDTO dto : dataList) {
            LocalDate date = LocalDate.parse(dto.getDate());

            if(dto.getBreakfast() != null) {
                saveSelectedMenu(dto.getBreakfast(), findMeal(date, MealType.BREAKFAST));
            }

            if(dto.getLunch() != null) {
                saveSelectedMenu(dto.getLunch(), findMeal(date, MealType.LUNCH));
            }

            if(dto.getDinner() != null) {
                saveSelectedMenu(dto.getDinner(), findMeal(date, MealType.DINNER));
            }
        }
    }

    private void saveSelectedMenu(MndMenuDTO menuDto, Meal meal) {
        Menu menu = findMenu(menuDto.getName(), menuDto.getKcal());
        selectedMenuRepository.save(SelectedMenu.of(meal, menu));
    }

    private Meal findMeal(LocalDate date, MealType type) {
        Meal meal = mealRepository.findByDateAndMealType(date, type);
        if(meal == null) {
            return mealRepository.save(Meal.of(date, type));
        }
        return meal;
    }

    private Menu findMenu(String name, double kcal) {
        Menu menu = menuRepository.findByName(name);
        if(menu == null) {
            return menuRepository.save(Menu.of(name, kcal));
        }
        return menu;
    }
}
