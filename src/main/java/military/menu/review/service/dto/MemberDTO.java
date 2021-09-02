package military.menu.review.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.Role;

@Getter
@Setter
@ToString
public class MemberDTO {
    private String username;
    private String password;
    private String name;
    private String militaryId;
    private String role;

    public MemberDTO() {}

    public MemberDTO(Member member) {
        this.username = member.getUsername();
        this.password = "[PROTECTED]";
        this.name = member.getName();
        this.militaryId = member.getMilitaryId();
        this.role = member.getRole().name();
    }

    public Member toEntity() {
        return Member.of(username, password, name, militaryId, Role.valueOf(role));
    }
}
