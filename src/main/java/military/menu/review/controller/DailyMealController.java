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
@RequestMapping("/meals")
@RequiredArgsConstructor
public class DailyMealController {
    private final MenuService menuService;
    private final MealMenuService mealMenuService;

    @GetMapping("/{year}/{month}/{week}")
    public ResponseEntity<Map<String, Object>> meals(@PathVariable int year, @PathVariable int month, @PathVariable int week) {
        Week w = Week.of(year, month, week);
        return null;
    }
}
