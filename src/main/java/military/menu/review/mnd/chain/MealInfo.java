package military.menu.review.mnd.chain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.entity.MealType;

import java.time.LocalDate;

@RequiredArgsConstructor
@EqualsAndHashCode
public class MealInfo {
    private final LocalDate date;
    private final MealType type;
}
