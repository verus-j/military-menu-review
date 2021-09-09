package military.menu.review.ui.member;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.member.Member;

@Getter @Setter
public class MemberResponse {
    private Long id;
    private String username;
    private String name;
    private String type;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.name = member.getName();
        this.type = member.getMemberType().name();
    }
}
