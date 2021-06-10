package military.menu.review.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Component
public class JWTUtils {
    public static final String HEADER = "Authentication";
    public static final String BEARER = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifeTime}")
    private Long lifeTime;
    private Algorithm algorithm;


    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC512(secret);
    }

    public String generate(String username) {
        return JWT.create()
            .withSubject(username)
            .withClaim("exp", Instant.now().getEpochSecond() + lifeTime)
            .sign(algorithm);
    }

    public VerifyResult verify(String token) {
        try {
            DecodedJWT result = JWT.require(algorithm).build().verify(token);
            return VerifyResult.builder().username(result.getSubject()).lifeTime(result.getClaim("exp").asLong()).isVerified(true).build();
        } catch(Exception e) {
            DecodedJWT result = JWT.decode(token);
            return VerifyResult.builder().username(result.getSubject()).isVerified(false).build();
        }
    }
}
