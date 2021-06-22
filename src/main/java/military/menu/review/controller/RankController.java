package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Week;
import military.menu.review.service.RankService;
import military.menu.review.service.dto.RankDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/rank")
@RestController
@RequiredArgsConstructor
public class RankController {
    private final RankService rankService;

    @GetMapping("/top10")
    public ResponseEntity<Map<String, Object>> top10(@ModelAttribute Week week) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", rankService.top10(week));
        return ResponseEntity.ok(map);
    }
}
