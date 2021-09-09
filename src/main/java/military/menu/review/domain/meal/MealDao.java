package military.menu.review.domain.meal;

import military.menu.review.domain.meal.MealDto;
import military.menu.review.domain.member.Member;

import java.time.LocalDate;
import java.util.List;

public interface MealDao {
    List<MealDto> selectByDateBetweenWithIsLiked(LocalDate start, LocalDate end, Member member);

    MealDto selectByIdWithIsLiked(Long id, Member member);
}
