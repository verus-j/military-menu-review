package military.menu.review.service.mnd.filter.impl;

import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DailyMealDTOUtils {
    public static final LocalDate DATE1 = LocalDate.of(2021, 6, 11);
    public static final LocalDate DATE2 = LocalDate.of(2021, 6, 12);

    public static List<DailyMealDTO> dailyMealDtos() {
        return Arrays.asList(dto1(), dto2());
    }

    public static DailyMealDTO dto1() {
        DailyMealDTO dto = DailyMealDTO.of(DATE1);
        dto.addBreakfastMenu(MenuDTO.of("밥", 111));
        dto.addLunchMenu(MenuDTO.of("조밥", 111));
        dto.addLunchMenu(MenuDTO.of("김치", 111));
        dto.addDinnerMenu(MenuDTO.of("라면", 111));
        return dto;
    }

    public static DailyMealDTO dto2() {
        DailyMealDTO dto = DailyMealDTO.of(DATE2);
        dto.addBreakfastMenu(MenuDTO.of("카레", 111));
        dto.addLunchMenu(MenuDTO.of("볶음밥", 111));
        return dto;
    }
}
