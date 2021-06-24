package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.*;
import military.menu.review.repository.LikeRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.repository.MemberRepository;
import military.menu.review.repository.MenuRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final MealService mealService;
    private final MenuService menuService;
    private final MemberService memberService;

    public void like(Long mealId, Long menuId) {
        Member member = memberService.getCurrentMember();
        Meal meal = mealService.findById(mealId);
        Menu menu = menuService.findById(menuId);
        Week week = Week.from(meal.getDate());

        if(likeRepository.findByMemberAndMenuAndWeek(member, menu, week) != null) {
            throw new IllegalArgumentException();
        }

        likeRepository.save(Like.of(member, menu, week));
        menu.like();
    }

    public void unlike(Long mealId, Long menuId) {
        Member member = memberService.getCurrentMember();
        Meal meal = mealService.findById(mealId);
        Menu menu = menuService.findById(menuId);
        Week week = Week.from(meal.getDate());
        Like like = likeRepository.findByMemberAndMenuAndWeek(member, menu, week);

        if(like == null) {
            throw new IllegalArgumentException();
        }

        likeRepository.delete(like);
        menu.unlike();
    }
}
