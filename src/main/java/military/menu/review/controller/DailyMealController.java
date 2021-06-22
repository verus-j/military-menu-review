package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Week;
import military.menu.review.service.MenuService;
import military.menu.review.service.DailyMealService;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/daily-meal")
@RequiredArgsConstructor
public class DailyMealController {
    private final DailyMealService dailyMealService;
    private final MenuService menuService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(@ModelAttribute Week week) {
        Map<String, Object> map = new HashMap<>();
        map.put("meal", dailyMealService.findByDateBetween(week.firstDate(), week.lastDate()));
        map.put("liked", menuService.findIdByMemberLikedDuringWeek(week));
        return ResponseEntity.ok(map);
    }
}
