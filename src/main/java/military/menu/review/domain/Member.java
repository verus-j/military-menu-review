package military.menu.review.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue @Column(name="member_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String name;
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;
}
