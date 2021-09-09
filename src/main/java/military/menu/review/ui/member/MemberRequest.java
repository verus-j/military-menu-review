package military.menu.review.ui.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.member.MemberType;
import military.menu.review.domain.member.Member;

import javax.validation.constraints.NotNull;

@Getter @Setter @Builder
public class MemberRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String type;

    public Member toEntity() {
        return Member.of(username, password, name, MemberType.valueOf(type));
    }
}
