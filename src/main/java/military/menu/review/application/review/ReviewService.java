package military.menu.review.application.review;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.meal.MealRepository;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.review.Review;
import military.menu.review.domain.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review create(Member member, Meal meal, String content) {
        return reviewRepository.save(Review.of(member, meal, content));
    }

    public Review update(Review review, String content) {
        review.editContent(content);
        return review;
    }
}
