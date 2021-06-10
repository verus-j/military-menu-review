package military.menu.review.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyResult {
    private String username;
    private Long lifeTime;
    private boolean isVerified;

}
