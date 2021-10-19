package military.menu.review.service.mnd.filter;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.MealType;

import java.time.LocalDate;

@RequiredArgsConstructor
@EqualsAndHashCode
public class MealInfo {
    private final LocalDate date;
    private final MealType type;
}
