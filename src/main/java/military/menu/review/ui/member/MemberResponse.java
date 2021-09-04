package military.menu.review.controller.member;

import lombok.Getter;
import lombok.Setter;
import military.menu.review.domain.member.Member;

@Getter @Setter
public class MemberResponse {
    private Long id;
    private String username;
    private String name;
    private String role;
    private String militaryId;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.name = member.getName();
        this.role = member.getRole().name();
        this.militaryId = member.getMilitaryId();
    }
}
