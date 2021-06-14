package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Meal;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.service.LikeService;
import military.menu.review.service.MealService;
import military.menu.review.service.MemberService;
import military.menu.review.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/menu")
@RestController
public class LikeController {
    private final MemberService memberService;
    private final MealService mealService;
    private final LikeService likeService;
    private final MenuService menuService;

    @PostMapping("/like")
    public ResponseEntity like(@RequestParam Long mealId, @RequestParam Long menuId) {
        Member member = memberService.getCurrentMember();
        likeService.like(mealId, menuId, member);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/unlike")
    public ResponseEntity unlike(@RequestParam int mealId, @RequestParam int menuId) {
        Member member = memberService.getCurrentMember();
        Meal meal = mealService.findById(mealId);
        Menu menu = menuService.findById(menuId);
        likeService.unlike(meal, menu, member);

        return ResponseEntity.ok("success");
    }
}
