package military.menu.review.app.member;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.member.MemberAdapter;
import military.menu.review.domain.member.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Member join(Member member) {
        if(memberRepository.findByUsername(member.getUsername()) != null) {
            throw new UsernameNotFoundException(String.format("%s의 아이디를 가진 유저는 없습니다.", member.getUsername()));
        }
        member.encodePassword(encoder);
        return memberRepository.save(member);
    }

    public Member getCurrentMember() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (Member)token.getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);

        if(member == null) {
            throw new UsernameNotFoundException(username + " is not found");
        }

        return new MemberAdapter(member);
    }
}
