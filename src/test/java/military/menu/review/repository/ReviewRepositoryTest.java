package military.menu.review.repository;

import com.mysema.query.types.Order;
import military.menu.review.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@DataJpaTest
@ActiveProfiles("test")
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired 
    private MealRepository mealRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;
    
    @Test
    public void shouldFindByMeal() {
        Meal meal = Meal.of(MealType.DINNER, LocalDate.of(2021, 6, 25));
        Member member = Member.of("username", "password", "name", "11-1111", Role.SOLDIER);

        mealRepository.save(meal);
        memberRepository.save(member);

        Review review1 = Review.of("content1", LocalDateTime.now(), meal, member);
        Review review2 = Review.of("content2", LocalDateTime.now(), meal, member);
        Review review3 = Review.of("content3", LocalDateTime.now(), meal, member);
        Review review4 = Review.of("content4", LocalDateTime.now(), meal, member);
        Review review5 = Review.of("content5", LocalDateTime.now(), meal, member);
        Review review6 = Review.of("content6", LocalDateTime.now(), meal, member);
        Review review7 = Review.of("content7", LocalDateTime.now(), meal, member);

        reviewRepository.saveAll(
                Arrays.asList(review1, review2, review3, review4, review5, review6, review7)
        );

        em.flush();
        em.clear();

        Page<Review> page1 = reviewRepository.findByMeal(meal, PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "created")));
        Page<Review> page2 = reviewRepository.findByMeal(meal, PageRequest.of(1, 4, Sort.by(Sort.Direction.ASC, "created")));

        assertThat(page1.hasNext(), is(true));
        assertThat(page2.hasNext(), is(false));

        assertThat(page1.getContent(), is(Arrays.asList(
                review1, review2, review3, review4
        )));
        assertThat(page2.getContent(), is(Arrays.asList(
                review5, review6, review7
        )));

    }
}
