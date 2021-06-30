package military.menu.review.service.mnd;

import military.menu.review.domain.Meal;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.MealType;
import military.menu.review.domain.Menu;
import military.menu.review.repository.MealMenuRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.repository.menu.MenuRepository;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.mnd.filter.MndRestProcessFilter;
import military.menu.review.service.mnd.filter.MndSaveFilterBuilder;
import military.menu.review.service.mnd.filter.impl.RestMndDataFilter;
import military.menu.review.service.mnd.filter.impl.SaveMealMenusFilter;
import military.menu.review.service.mnd.filter.impl.SaveMealsFilter;
import military.menu.review.service.mnd.filter.impl.SaveMenusFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
public class MndServiceTest {
    private String apiResult = "{\"DS_TB_MNDT_DATEBYMLSVC_ATC\":{\"list_total_count\":3,\"row\":[{\"dinr_cal\":\"545.69kcal\",\"lunc\":\"밥\",\"sum_cal\":\"2320.73kcal\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"2021-06-24\",\"lunc_cal\":\"363kcal\",\"brst\":\"밥\",\"dinr\":\"잡채밥(05)(06)(10)\",\"brst_cal\":\"363kcal\"},{\"dinr_cal\":\"24.58kcal\",\"lunc\":\"부대찌개(05)(06)(10)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"2021-06-25\",\"lunc_cal\":\"221.33kcal\",\"brst\":\"생선묵국(05)(06)\",\"dinr\":\"두부김치국(05)\",\"brst_cal\":\"74.78kcal\"},{\"dinr_cal\":\"23.22kcal\",\"lunc\":\"갑오징어야채볶음(05)(06)(17)\",\"sum_cal\":\"\",\"adspcfd\":\"\",\"adspcfd_cal\":\"\",\"dates\":\"2021-06-25\",\"lunc_cal\":\"180.34kcal\",\"brst\":\"닭간장소스찜(05)(06)(15)\",\"dinr\":\"부추겉절이\",\"brst_cal\":\"150.88kcal\"}]}}";
    private String[] menuNames = {"밥", "잡채밥", "생선묵국", "부대찌개", "두부김치국", "닭간장소스찜", "갑오징어야채볶음", "부추겉절이"};
    private double[] menuKcals = {363.0, 545.69, 74.78, 221.33, 24.58, 150.88, 180.34, 23.22};
    private LocalDate[] mealDates = {LocalDate.of(2021, 6, 24), LocalDate.of(2021, 6, 25)};

    @Autowired
    private MealMenuRepository mealMenuRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void shouldSaveMealMenus() {
        MndService mndService = new MndService(filter());
        mndService.saveFromApi();

        assertMenus();
        assertMeals();
        assertMealMenus();
    }

    private MndRestProcessFilter filter() {
        return new MndSaveFilterBuilder(new RestMndDataFilter(mndApi()))
            .addChain(new SaveMenusFilter(menuRepository))
            .addChain(new SaveMealsFilter(mealRepository))
            .addChain(new SaveMealMenusFilter(mealMenuRepository))
            .build();
    }

    private MndApi mndApi() {
        MndApi api = new MndApi();
        api.setTemplate(mockRestTemplate());
        return api;
    }

    private RestTemplate mockRestTemplate() {
        RestTemplate template = mock(RestTemplate.class);
        when(template.getForObject(any(String.class), eq(String.class))).thenReturn(apiResult);
        return template;
    }

    private void assertMenus() {
        List<Menu> menus = menuRepository.findAll();
        List<Menu> expected = new ArrayList<>();

        for(int i = 0; i < menuNames.length; i++) {
            expected.add(Menu.of(menuNames[i], menuKcals[i]));
        }

        assertThat(menus.size(), is(8));
        assertThat(menus, containsInAnyOrder(expected.toArray()));
    }

    private void assertMeals() {
        List<Meal> meals = mealRepository.findAll();
        List<Meal> expected = new ArrayList<>();

        for(LocalDate date : mealDates) {
            expected.add(Meal.of(MealType.BREAKFAST, date));
            expected.add(Meal.of(MealType.LUNCH, date));
            expected.add(Meal.of(MealType.DINNER, date));
        }

        assertThat(meals.size(), is(6));
        assertThat(meals, containsInAnyOrder(expected.toArray()));

    }

    private void assertMealMenus() {
        List<MealMenu> mealMenus = mealMenuRepository.findAll();
        List<MealMenu> expected = new ArrayList<>();
        expected.addAll(mealMenus1());
        expected.addAll(mealMenus2());

        assertThat(mealMenus.size(), is(9));
        assertThat(mealMenus, containsInAnyOrder(expected.toArray()));
    }

    private List<MealMenu> mealMenus1() {
        LocalDate date = LocalDate.of(2021, 6, 24);
        Meal breakfast = Meal.of(MealType.BREAKFAST, date);
        Meal lunch = Meal.of(MealType.LUNCH, date);
        Meal dinner = Meal.of(MealType.DINNER, date);

        Menu menu1 = Menu.of("밥", 363.0);
        Menu menu2 = Menu.of("잡채밥", 545.69);

        return Arrays.asList(
                MealMenu.of(breakfast, menu1, 1), MealMenu.of(lunch, menu1, 1), MealMenu.of(dinner, menu2, 1)
        );
    }

    private List<MealMenu> mealMenus2() {
        LocalDate date = LocalDate.of(2021, 6, 25);
        Meal breakfast = Meal.of(MealType.BREAKFAST, date);
        Meal lunch = Meal.of(MealType.LUNCH, date);
        Meal dinner = Meal.of(MealType.DINNER, date);

        Menu menu1 = Menu.of("생선묵국", 74.78);
        Menu menu2 = Menu.of("부대찌개", 221.33);
        Menu menu3 = Menu.of("두부김치국", 24.58);
        Menu menu4 = Menu.of("닭간장소스찜", 150.88);
        Menu menu5 = Menu.of("갑오징어야채볶음", 180.34);
        Menu menu6 = Menu.of("부추겉절이", 23.22);

        return Arrays.asList(
                MealMenu.of(breakfast, menu1, 1), MealMenu.of(lunch, menu2, 1), MealMenu.of(dinner, menu3,1),
                MealMenu.of(breakfast, menu4, 2), MealMenu.of(lunch, menu5, 2), MealMenu.of(dinner, menu6, 2)
        );
    }
}
