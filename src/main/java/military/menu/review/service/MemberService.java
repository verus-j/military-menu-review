package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Member findByUsername(String username) {
        Member m = memberRepository.findByUsername(username);
        m.removePassword();
        return m;
    }



    public void join(Member member) {
        member.encodePassword(encoder);
        memberRepository.save(member);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);

        if(member == null) {
            throw new UsernameNotFoundException(username + " is not found");
        }

        return User.builder()
            .username(member.getUsername())
            .password(member.getPassword())
            .authorities(new SimpleGrantedAuthority(member.getRole().name()))
            .build();
    }
}
