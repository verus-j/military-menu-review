package military.menu.review.security;

import lombok.Getter;
import lombok.ToString;
import military.menu.review.domain.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@ToString
public class MemberDTO {
    private String username;
    private Role role;

    public MemberDTO(UsernamePasswordAuthenticationToken user) {
        this.username = (String) user.getPrincipal();
        this.role = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(Role::valueOf).findFirst().get();
    }
}
