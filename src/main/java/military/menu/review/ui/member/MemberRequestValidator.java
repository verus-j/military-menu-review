package military.menu.review.ui.member;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MemberRequestValidator {
    public void validate(MemberRequest memberRequest, Errors errors) {
        if(!(memberRequest.getRole().equals("NORMAL") || memberRequest.getRole().equals("SOLDIER"))) {
            errors.rejectValue("memberRequest", "role", "유형은 민간인[NORMAL] 또는 군인[SOLDIER]만 가능합니다.");
            return;
        }

        if(memberRequest.getRole().equals("NORMAL") && !memberRequest.getMilitaryId().isEmpty()) {
            errors.rejectValue("memberRequest", "militaryId", "민간인은 군번을 가질 수 없습니다.");
        }

        if(memberRequest.getRole().equals("SOLDIER") && memberRequest.getMilitaryId().isEmpty()) {
            errors.rejectValue("memberRequest", "militaryId", "군인은 군번이 필요합니다.");
        }
    }
}
