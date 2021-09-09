package military.menu.review.domain.member;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="member_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    protected Member(){

    }

    private Member(String username, String password, String name, MemberType memberType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.memberType = memberType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(username, member.username) && memberType == member.memberType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, memberType);
    }

    public static Member of(String username, String password, String name, MemberType memberType) {
        return new Member(username, password, name, memberType);
    }

    public void encodePassword(PasswordEncoder encoder) {
        password = encoder.encode(password);
    }
}
