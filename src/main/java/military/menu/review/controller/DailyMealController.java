package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Week;
import military.menu.review.service.MemberService;
import military.menu.review.service.MenuService;
import military.menu.review.service.DailyMealService;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dailyMeal")
@RequiredArgsConstructor
public class DailyMealController {
    private final DailyMealService dailyMealService;
    private final MenuService menuService;
    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(@ModelAttribute Week week) {
        Map<String, Object> map = new HashMap<>();
        map.put("meal", dailyMealService.findByDateBetween(week.firstDate(), week.lastDate()));
        map.put("liked", menuService.findByMemberLikedDuringWeek(memberService.getCurrentMember(), week).stream().map(MenuDTO::getId).collect(Collectors.toList()));
        return ResponseEntity.ok(map);
    }
}
