package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Week;
import military.menu.review.service.MenuService;
import military.menu.review.service.MealMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/daily-meal")
@RequiredArgsConstructor
public class DailyMealController {
    private final MenuService menuService;
    private final MealMenuService mealMenuService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(@ModelAttribute Week week) {
        Map<String, Object> map = new HashMap<>();
        map.put("meal", mealMenuService.findByDateBetween(week.firstDate(), week.lastDate()));
        map.put("liked", menuService.findMemberLikedIdDuringWeek(week));
        return ResponseEntity.ok(map);
    }
}
