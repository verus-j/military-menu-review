package military.menu.review.ui.member;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MemberRequestValidator {
    public void validate(MemberRequest memberRequest, Errors errors) {
        if(!memberRequest.getType().equals("SOLDIER")) {
            errors.rejectValue("memberRequest", "role", "유형은 군인[SOLDIER]만 가능합니다.");
            return;
        }
    }
}
