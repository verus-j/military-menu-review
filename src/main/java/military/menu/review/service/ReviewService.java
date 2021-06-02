package military.menu.review.service;

import military.menu.review.model.menu.Review;
import military.menu.review.model.menu.User;
import military.menu.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Service
public class ReviewService {
    private ReviewRepository repository;

    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Page<Review> findByMenuList(Long id, Pageable pageable) {
        return repository.findAllMenuListId(id, pageable);
    }

    public void writeReview(User user, String content) {
        Review review = new Review(user, content, LocalDate.now());
        repository.save(review);
    }
}
