package military.menu.review.ui.meal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;

@Component
public class WeekValidator {
    public void validate(WeekRequest weekRequest, Errors errors) {
        LocalDate firstDate = weekRequest.firstDate();

        if(firstDate.getMonthValue() != weekRequest.getMonth()){
            errors.rejectValue("week", String.format("%d년 %d월 %d주 정보가 잘못됐습니다.", weekRequest.getYear(), weekRequest.getMonth(), weekRequest.getWeek()));
        }
    }
}
