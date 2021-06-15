package military.menu.review.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenCheckFilter extends BasicAuthenticationFilter {
    private final JWTUtils jwtUtils;
    private final MemberRepository memberRepository;

    public JwtTokenCheckFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, MemberRepository memberRepository) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.memberRepository = memberRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JWTUtils.HEADER);

        if(token == null || !token.startsWith(JWTUtils.BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        VerifyResult result = jwtUtils.verify(token.substring(JWTUtils.BEARER.length()));

        if(result.isVerified()) {
            if(result.getLifeTime() - Instant.now().getEpochSecond() < 30) {
                String newToken = jwtUtils.generate(result.getUsername());
                response.addHeader(JWTUtils.HEADER, newToken);
            }

            Member member = memberRepository.findByUsername(result.getUsername());
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(member.getRole().name()));

            System.out.println(member);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    member.getUsername(), null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        chain.doFilter(request, response);
    }
}
