package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.security.MemberContext;
import military.menu.review.service.MenuService;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.mnd.api.MndApi;
import military.menu.review.service.DailyMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dailyMeal")
@RequiredArgsConstructor
public class DailyMealController {
    private final MndApi api;
    private final DailyMealService dailyMealService;
    private final MenuService menuService;
    private final MemberContext context;

    @GetMapping("/one")
    public ResponseEntity<Map<String, Object>> dailyMeal(@RequestParam String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("meal", dailyMealService.findByDate(toDate(date)));
        map.put("liked", menuService.findByMemberLikedAndDate(context, toDate(date)));

        return ResponseEntity.ok(map);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(@RequestParam String start, @RequestParam String end) {
        System.out.println(context.getCurrentMember());
        Map<String, Object> map = new HashMap<>();
        map.put("meal", dailyMealService.findByDateBetween(toDate(start), toDate(end)));
        map.put("liked", menuService.findByMemberLikedAndDateBetween(context, toDate(start), toDate(end)));
        return ResponseEntity.ok(map);
    }

    private LocalDate toDate(String date) {
        return LocalDate.parse(date);
    }
}
