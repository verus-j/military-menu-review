package military.menu.review.ui.review;

import lombok.RequiredArgsConstructor;
import military.menu.review.application.review.ReviewService;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.meal.MealRepository;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.review.Review;
import military.menu.review.domain.review.ReviewRepository;
import military.menu.review.security.CurrentMember;
import military.menu.review.ui.review.exception.BlankContentException;
import military.menu.review.ui.review.exception.CurrentMemberIsAnonymousException;
import military.menu.review.ui.review.exception.NotCreateMemberException;
import military.menu.review.ui.review.exception.NotFoundEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meals/{id}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final MealRepository mealRepository;

    @ExceptionHandler(BlankContentException.class)
    public ResponseEntity badRequest() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({CurrentMemberIsAnonymousException.class, NotCreateMemberException.class})
    public ResponseEntity unauthorized() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity notFound() {
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity create(@PathVariable Long id,
                                 @CurrentMember Member member,
                                 @Valid @RequestBody ReviewRequest reviewRequest,
                                 Errors errors) {
        checkMemberIsNull(member);
        checkReviewRequest(errors);
        Meal meal = findEntity(mealRepository.findById(id));

        Review review = reviewService.create(member, meal, reviewRequest.getContent());
        URI location = linkTo(ReviewController.class, id).slash(review.getId()).toUri();
        return ResponseEntity.created(location).body(new ReviewResponse(review, member));
    }

    @GetMapping
    public ResponseEntity queryReviews(@PathVariable Long id,
                                       @CurrentMember Member member,
                                       Pageable pageable,
                                       PagedResourcesAssembler<Review> assembler) {
        Page<Review> reviews = reviewRepository.findAllByMealId(pageable, id);
        PagedModel<ReviewResponse> pagedModel = assembler.toModel(reviews, r -> new ReviewResponse(r, member));
        if(member != null) {
            pagedModel.add(linkTo(ReviewController.class, id).withRel("create-review"));
        }
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity queryReview(@PathVariable Long id, @PathVariable Long reviewId, @CurrentMember Member member) {
        Review review = findEntity(reviewRepository.findById(reviewId));
        checkReviewHasSameMealId(review, id);
        return ResponseEntity.ok(new ReviewResponse(review, member));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long id, @PathVariable Long reviewId, @CurrentMember Member member) {
        Review review = findEntity(reviewRepository.findById(reviewId));
        checkReviewHasSameMealId(review, id);
        checkMemberIsNull(member);
        checkReviewIsCreatedMember(review, member);
        reviewService.delete(review);
        RepresentationModel model = new RepresentationModel();
        model.add(linkTo(ReviewController.class, id).withRel("reviews"));
        model.add(linkTo(ReviewController.class, id).withRel("create-review"));
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long id,
                                       @PathVariable Long reviewId,
                                       @CurrentMember Member member,
                                       @Valid @RequestBody ReviewRequest reviewRequest,
                                       Errors errors) {
        checkReviewRequest(errors);
        Review review = findEntity(reviewRepository.findById(reviewId));
        checkReviewHasSameMealId(review ,id);
        checkMemberIsNull(member);
        checkReviewIsCreatedMember(review, member);
        return ResponseEntity.ok(new ReviewResponse(reviewService.update(review, reviewRequest.getContent()), member));
    }

    private <T> T findEntity(Optional<T> optional) {
        if(!optional.isPresent()) {
            throw new NotFoundEntityException();
        }
        return optional.get();
    }

    private void checkReviewRequest(Errors errors) {
        if(errors.hasErrors()) {
            throw new BlankContentException();
        }
    }

    private void checkMemberIsNull(Member member) {
        if(member == null) {
            throw new CurrentMemberIsAnonymousException();
        }
    }

    private void checkReviewHasSameMealId(Review review, Long mealId) {
        if(!review.getMeal().getId().equals(mealId)) {
            throw new NotFoundEntityException();
        }
    }

    private void checkReviewIsCreatedMember(Review review, Member member) {
        if(!review.getMember().getId().equals(member.getId())) {
            throw new NotCreateMemberException();
        }
    }
}
