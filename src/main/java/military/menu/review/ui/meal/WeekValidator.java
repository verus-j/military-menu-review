package military.menu.review.ui.meal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class WeekValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(WeekRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WeekRequest weekRequest = (WeekRequest)target;
        LocalDate firstDate = weekRequest.firstDate();

        if(firstDate.getMonthValue() != weekRequest.getMonth()){
            errors.rejectValue("week", String.format("%d년 %d월 %d주 정보가 잘못됐습니다.", weekRequest.getYear(), weekRequest.getMonth(), weekRequest.getWeek()));
        }
    }
}
