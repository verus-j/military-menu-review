package military.menu.review.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import military.menu.review.repository.MemberRepository;
import military.menu.review.security.JWTUtils;
import military.menu.review.security.JwtLoginFilter;
import military.menu.review.security.JwtTokenCheckFilter;
import military.menu.review.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;
    private final JWTUtils jwtUtils;
    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/dailyMeal/**").authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .cors().disable();

        http.addFilterAt(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtTokenCheckFilter(), BasicAuthenticationFilter.class);
    }

    private JwtLoginFilter jwtLoginFilter() throws Exception {
        JwtLoginFilter filter = new JwtLoginFilter(objectMapper, super.authenticationManager(), jwtUtils);
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    private JwtTokenCheckFilter jwtTokenCheckFilter() throws Exception {
        return new JwtTokenCheckFilter(super.authenticationManager(), jwtUtils, memberRepository);
    }
}
