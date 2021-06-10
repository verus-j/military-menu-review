package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);

        if(member == null) {
            throw new UsernameNotFoundException(username + " is not found");
        }

        return User.builder()
            .username(member.getUsername())
            .password(member.getPassword())
            .authorities(new SimpleGrantedAuthority(member.getRole().getName()))
            .build();
    }
}
