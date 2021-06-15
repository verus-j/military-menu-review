package military.menu.review.repository;

import military.menu.review.domain.Meal;
import military.menu.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByMeal(Meal meal, Pageable pageable);
}
