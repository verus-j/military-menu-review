package military.menu.review.controller.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MemberRequestValidatorTest {
    private Errors errors;

    @BeforeEach
    void setUp() {
        errors = mock(Errors.class);
    }

    @Test
    @DisplayName("유효한 사용자 유형 값만 사용할 수 있다.")
    public void invalidRole() throws Exception {
        MemberRequestValidator validator = new MemberRequestValidator();
        MemberRequest request = MemberRequest.builder()
                .role("INVALID")
                .build();
        validator.validate(request, errors);

        verify(errors, times(1)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("민간인 유형은 군번을 가질 수 없다.")
    public void normalTypeNotHasMilitaryId() throws Exception {
        MemberRequestValidator validator = new MemberRequestValidator();
        MemberRequest request = MemberRequest.builder()
                .role("NORMAL")
                .militaryId("11-11111")
                .build();
        validator.validate(request, errors);

        verify(errors, times(1)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("군인 유형은 군번을 꼭 가져야한다.")
    public void soldierTypeMustHasMilitaryId() throws Exception {
        MemberRequestValidator validator = new MemberRequestValidator();
        MemberRequest request = MemberRequest.builder()
                .role("SOLDIER")
                .militaryId("")
                .build();
        validator.validate(request, errors);

        verify(errors, times(1)).rejectValue(anyString(), anyString(), anyString());
    }

}
