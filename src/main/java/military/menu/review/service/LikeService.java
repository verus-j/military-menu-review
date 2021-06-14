package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.*;
import military.menu.review.repository.LikeRepository;
import military.menu.review.repository.MealRepository;
import military.menu.review.repository.MemberRepository;
import military.menu.review.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final MealService mealService;
    private final MenuService menuService;
    private final MemberRepository memberRepository;

    public void like(Long mealId, Long menuId, Member m) {
        Member member = memberRepository.save(m);
        Meal meal = mealService.findById(mealId);
        Menu menu = menuService.findById(menuId);
        Week week = Week.from(meal.getDailyMeal().getDate());
        menu.like();
        likeRepository.save(Like.of(member, menu, week));
    }

    public void unlike(Meal meal, Menu menu, Member member) {
        Week week = Week.from(meal.getDailyMeal().getDate());
        menu.unlike();
        Like like = likeRepository.findByMemberAndMenuAndWeek(member, menu, week);
        likeRepository.delete(like);
    }
}
