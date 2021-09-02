package military.menu.review.domain.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MemberAdapter extends User {
    private Member member;

    public MemberAdapter(Member member) {
        super(member.getUsername(), member.getPassword(), authorities(member));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    private static Set<GrantedAuthority> authorities(Member member) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        return authorities;
    }
}
