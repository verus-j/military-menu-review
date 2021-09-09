package military.menu.review.ui.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
                .type("INVALID")
                .build();
        validator.validate(request, errors);

        verify(errors, times(1)).rejectValue(anyString(), anyString(), anyString());
    }

}
