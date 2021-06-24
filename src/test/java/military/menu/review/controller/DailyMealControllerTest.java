package military.menu.review.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.domain.Week;
import military.menu.review.service.MenuService;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.service.MealMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class DailyMealControllerTest {
    private MockMvc mvc;

    @MockBean private MealMenuService mealMenuService;
    @MockBean private MenuService menuService;

    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new DailyMealController(menuService, mealMenuService)).build();

        Week week = new Week(2021, 6, 1);
        when(mealMenuService.findByDateBetween(week.firstDate(), week.lastDate())).thenReturn(dailyMeals());
        when(menuService.findIdByMemberLikedDuringWeek(new Week(2021, 6, 1))).thenReturn(menusId());
    }

    private List<DailyMealDTO> dailyMeals() {
        List<DailyMealDTO> dailyMeals = new ArrayList<>();
        Week week = new Week(2021, 6, 1);
        LocalDate now = week.firstDate();

        while(week.lastDate().isBefore(now) || week.lastDate().isEqual(now)) {
            DailyMealDTO dto = DailyMealDTO.of(now);
            dto.addBreakfastMenu(MenuDTO.of("밥", 111));
            dto.addLunchMenu(MenuDTO.of("조밥", 222));
            dto.addDinnerMenu(MenuDTO.of("김치", 333));
            now = now.plusDays(1);
        }

        return dailyMeals;
    }

    private List<Long> menusId() {
        return Arrays.asList(1L, 3L);
    }

    @Test
    public void shouldReturnMealAndLiked() throws Exception {
        mvc.perform(get("/daily-meal/list")
                .param("year", "2021")
                .param("month", "6")
                .param("week", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(), false))
                .andExpect(content().contentType("application/json"));

    }

    private String toJson() {
        try {
            return objectMapper.writeValueAsString(toMap());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        throw new AssertionError();
    }

    private Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("meal", dailyMeals());
        map.put("liked", menusId());
        return map;
    }
}
