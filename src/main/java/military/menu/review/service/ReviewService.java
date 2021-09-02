package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.Review;
import military.menu.review.repository.ReviewRepository;
import military.menu.review.service.dto.ReviewPageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final MemberService memberService;
    private final ReviewRepository reviewRepository;
    private final MealService mealService;

    public ReviewPageDTO findReviewPageByMeal(Long mealId, int page, int size) {
        Meal meal = mealService.findById(mealId);
        Page<Review> result = reviewRepository.findByMeal(meal, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "created")));
        return new ReviewPageDTO(result, memberService.getCurrentMember());
    }

    public void writeReview(Long mealId, String content) {
        Meal meal = mealService.findById(mealId);
        Member member = memberService.getCurrentMember();
        Review review = Review.of(content, LocalDateTime.now(), meal, member);
        reviewRepository.save(review);
    }
}
