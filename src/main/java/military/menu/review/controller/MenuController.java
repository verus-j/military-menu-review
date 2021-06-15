package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Menu;
import military.menu.review.service.LikeService;
import military.menu.review.service.MenuService;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final LikeService likeService;
    private final MenuService menuService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", menuService.findAll());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/like")
    public ResponseEntity like(@RequestBody Map<String, Long> map) {
        Long mealId = map.get("mealId");
        Long menuId = map.get("menuId");
        likeService.like(mealId, menuId);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/unlike")
    public ResponseEntity unlike(@RequestBody Map<String, Long> map) {
        Long mealId = map.get("mealId");
        Long menuId = map.get("menuId");
        likeService.unlike(mealId, menuId);
        return ResponseEntity.ok("success");
    }
}
