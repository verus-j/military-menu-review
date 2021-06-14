package military.menu.review.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@ToString
public class Member {
    @Id @GeneratedValue @Column(name="member_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String name;
    @Column(unique = true)
    private String militaryId;
    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member(){

    }

    private Member(String username, String password, String name, String militaryId, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.militaryId = militaryId;
        this.role = role;
    }

    public static Member of(String username, String password, String name, String militaryId, Role role) {
        return new Member(username, password, name, militaryId, role);
    }

    public void encodePassword(PasswordEncoder encoder) {
        password = encoder.encode(password);
    }
}
