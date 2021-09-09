package military.menu.review.infra.meal;

import military.menu.review.application.like.LikeService;
import military.menu.review.application.member.MemberService;
import military.menu.review.domain.like.Like;
import military.menu.review.domain.meal.*;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.member.MemberType;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuDto;
import military.menu.review.domain.menu.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MealDaoImplTest {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    SelectedMenuRepository selectedMenuRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    LikeService likeService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MealDaoImpl mealDao;

    @Test
    @DisplayName("식단표가 하나일 때 조회")
    public void selectHasOneMeal() throws Exception {
        LocalDate date = of(2021, 9, 7);
        MealType type = MealType.BREAKFAST;
        String[] names = {"a", "b"};
        double[] kcal = {1.0, 2.0};

        Meal meal = saveMeal(date, type);
        Menu menu1 = saveMenu(names[0], kcal[0]);
        Menu menu2 = saveMenu(names[1], kcal[1]);
        saveSelectedMenu(meal, menu1);
        saveSelectedMenu(meal, menu2);

        List<MealDto> actual = mealDao.selectByDateBetweenWithIsLiked(of(2021, 9, 5),
                of(2021, 9, 9), null);

        assertThat(actual).hasSize(1);

        MealDto dto = actual.get(0);
        assertThat(dto.getId()).isNotNull();
        assertThat(dto.getDate()).isEqualTo(date);
        assertThat(dto.getMealType()).isEqualTo(type);
        assertThat(dto.getMenus()).hasSize(2);

        int i = 0;
        for(MenuDto menuDto : dto.getMenus()) {
            assertThat(menuDto.getName()).isEqualTo(names[i]);
            assertThat(menuDto.getKcal()).isEqualTo(kcal[i]);
            i++;
        }
    }

    @Test
    @DisplayName("식단표가 여러개 일 때 조회")
    public void selectMoreMeals() throws Exception {
        LocalDate[] dates = {of(2021, 9, 6), of(2021, 9, 6), of(2021, 9, 6), of(2021, 9, 7)};
        MealType[] types = {MealType.BREAKFAST, MealType.DINNER, MealType.LUNCH, MealType.BREAKFAST};
        String[] name = {"a", "b", "c", "d"};
        double[] kcal = {1.0, 2.0, 3.0, 4.0};

        Meal meal1 = saveMeal(dates[0], types[0]);
        Meal meal2 = saveMeal(dates[1], types[1]);
        Meal meal3 = saveMeal(dates[2], types[2]);
        Meal meal4 = saveMeal(dates[3], types[3]);
        Menu menu1 = saveMenu(name[0], kcal[0]);
        Menu menu2 = saveMenu(name[1], kcal[1]);
        Menu menu3 = saveMenu(name[2], kcal[2]);
        Menu menu4 = saveMenu(name[3], kcal[3]);
        saveSelectedMenu(meal1, menu1);
        saveSelectedMenu(meal1, menu2);
        saveSelectedMenu(meal2, menu3);
        saveSelectedMenu(meal2, menu4);
        saveSelectedMenu(meal3, menu1);
        saveSelectedMenu(meal3, menu2);
        saveSelectedMenu(meal4, menu3);
        saveSelectedMenu(meal4, menu4);

        List<MealDto> actual = mealDao.selectByDateBetweenWithIsLiked(of(2021, 9, 6), of(2021, 9, 12), null);
        assertThat(actual).hasSize(4);

        for(int i = 0; i < 4; i++) {
            MealDto dto = actual.get(i);
            assertThat(dto.getId()).isNotNull();
            assertThat(dto.getDate()).isEqualTo(dates[i]);
            assertThat(dto.getMealType()).isEqualTo(types[i]);
            assertThat(dto.getMenus()).hasSize(2);

            for(int j = 0; j < 2; j++) {
                MenuDto menuDto = dto.getMenus().get(j);
                assertThat(menuDto.getName()).isEqualTo(name[(i%2)*2 + j]);
                assertThat(menuDto.getKcal()).isEqualTo(kcal[(i%2)*2 + j]);
                assertThat(menuDto.isLiked()).isFalse();
            }
        }
    }

    @Test
    @DisplayName("사용자 정보와 함께 조회")
    public void selectWithMember() throws Exception {
        String username = "wilgur513";
        String password = "pass";
        LocalDate[] dates = {of(2021, 9, 6), of(2021, 9, 8)};
        MealType[] types = {MealType.BREAKFAST, MealType.LUNCH};
        String[] name = {"a", "b", "c", "d"};
        double[] kcal = {1.0, 2.0, 3.0, 4.0};

        Member member = saveMember(username, password);
        Meal meal1 = saveMeal(dates[0], types[0]);
        Meal meal2 = saveMeal(dates[1], types[1]);
        Menu menu1 = saveMenu(name[0], kcal[0]);
        Menu menu2 = saveMenu(name[1], kcal[1]);
        Menu menu3 = saveMenu(name[2], kcal[2]);
        Menu menu4 = saveMenu(name[3], kcal[3]);
        saveLike(member, menu1);
        saveLike(member, menu3);
        saveSelectedMenu(meal1, menu1);
        saveSelectedMenu(meal1, menu2);
        saveSelectedMenu(meal2, menu3);
        saveSelectedMenu(meal2, menu4);

        List<MealDto> actual = mealDao.selectByDateBetweenWithIsLiked(of(2021, 9, 5), of(2021, 9, 9), member);
        assertThat(actual).hasSize(2);

        for(int i = 0; i < 2; i++) {
            MealDto dto = actual.get(i);
            assertThat(dto.getId()).isNotNull();
            assertThat(dto.getDate()).isEqualTo(dates[i]);
            assertThat(dto.getMealType()).isEqualTo(types[i]);
            assertThat(dto.getMenus()).hasSize(2);

            for(int j = 0; j < 2; j++) {
                MenuDto menuDto = dto.getMenus().get(j);
                assertThat(menuDto.getName()).isEqualTo(name[i*2 + j]);
                assertThat(menuDto.getKcal()).isEqualTo(kcal[i*2 + j]);
                if(j % 2 == 0) {
                    assertThat(menuDto.isLiked()).isTrue();
                } else {
                    assertThat(menuDto.isLiked()).isFalse();
                }
            }
        }
    }



    private Member saveMember(String username, String password) {
        return memberService.join(Member.of(username, password, "", MemberType.SOLDIER));
    }

    private Like saveLike(Member member, Menu menu) {
        return likeService.like(member, menu);
    }

    private Meal saveMeal(LocalDate date, MealType type) {
        Meal meal = Meal.of(date, type);
        return mealRepository.save(meal);
    }

    private Menu saveMenu(String name, double kcal) {
        return menuRepository.save(Menu.of(name, kcal));
    }

    private SelectedMenu saveSelectedMenu(Meal meal, Menu menu) {
        return selectedMenuRepository.save(SelectedMenu.of(meal, menu));
    }

}
