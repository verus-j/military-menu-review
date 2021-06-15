package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.ReviewService;
import military.menu.review.service.dto.ReviewPageDTO;
import military.menu.review.service.dto.ReviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/list")
    public ResponseEntity page(@RequestParam long mealId, @RequestParam int page, @RequestParam(value="size", defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.findReviewPageByMeal(mealId, page, size));
    }

    @PostMapping("/new")
    public ResponseEntity newReview(@RequestBody Map<String, String> map) {
        String content = map.get("content");
        Long mealId = Long.parseLong(map.get("mealId"));
        reviewService.writeReview(mealId, content);
        return ResponseEntity.ok("success");
    }
}
