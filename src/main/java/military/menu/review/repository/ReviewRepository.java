package military.menu.review.repository;

import military.menu.review.model.menu.DailyMenuType;
import military.menu.review.model.menu.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.menuList.id=:id")
    Page<Review> findAllMenuListId(@Param("id") Long Id, Pageable pageable);
}
