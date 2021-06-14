package military.menu.review.service.dto;

import lombok.Getter;
import lombok.ToString;
import military.menu.review.domain.Member;
import military.menu.review.domain.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@ToString
public class MemberDTO {
    private String username;
    private String password;
    private String name;
    private String militaryId;
    private String role;

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
