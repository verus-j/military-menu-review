package military.menu.review.ui.review;

import lombok.RequiredArgsConstructor;
import military.menu.review.application.review.ReviewService;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.meal.MealRepository;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.review.Review;
import military.menu.review.domain.review.ReviewRepository;
import military.menu.review.security.CurrentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

    @PostMapping
    public ResponseEntity create(@PathVariable Long id,
                                 @CurrentMember Member member,
                                 @Valid @RequestBody ReviewRequest reviewRequest,
                                 Errors errors) {
        if(member == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Meal> mealOptional = mealRepository.findById(id);
        if(!mealOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Review review = reviewService.create(member, mealOptional.get(), reviewRequest.getContent());
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
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity queryReview(@PathVariable Long id, @PathVariable Long reviewId, @CurrentMember Member member) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(!reviewOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Review review = reviewOptional.get();
        if(!review.getMeal().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ReviewResponse(review, member));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long id, @PathVariable Long reviewId, @CurrentMember Member member) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(!reviewOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Review review = reviewOptional.get();
        if(!review.getMeal().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        }
        if(!review.getMember().getId().equals(member.getId())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity updateReview(@PathVariable Long id,
                                       @PathVariable Long reviewId,
                                       @CurrentMember Member member,
                                       @Valid @RequestBody ReviewRequest reviewRequest,
                                       Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(!reviewOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Review review = reviewOptional.get();
        if(!review.getMeal().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        }
        if(!review.getMember().getId().equals(member.getId())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(new ReviewResponse(reviewService.update(review, reviewRequest.getContent()), member));
    }
}
