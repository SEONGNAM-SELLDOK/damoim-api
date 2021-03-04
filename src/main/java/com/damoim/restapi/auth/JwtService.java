package com.damoim.restapi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@Service
public class JwtService {

    // FIXME 환경변수로 받도록 빼야 함.
    private static final String SIGN_SECRET = "gEAAiEAu3X3OoILbAg0vdzjvc0MxIG0xP";
    private static final Algorithm SIGN_ALGORITHM = Algorithm.HMAC256(SIGN_SECRET);


    public JwtUser decode(String jwtToken) {
        DecodedJWT s = JWT.decode(jwtToken);
        JWTVerifier verifier = JWT.require(SIGN_ALGORITHM).build();

        verifier.verify(s);

        if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) > s.getClaim("expiryTime").asLong()) {
            throw new BadCredentialsException("expired token");
        }

        return JwtUser.of(s.getClaim("id").asLong(),
                LocalDateTime.ofEpochSecond(s.getClaim("issuedAt").asLong(), 0, ZoneOffset.UTC),
                LocalDateTime.ofEpochSecond(s.getClaim("expiryTime").asLong(), 0, ZoneOffset.UTC));
    }

    public String encode(JwtUser user) {
        return JWT.create().withJWTId(user.jti)
                .withClaim("id", user.userId)
                .withClaim("issuedAt", user.issuedAt.toEpochSecond(ZoneOffset.UTC))
                .withClaim("expiryTime", user.expiryTime.toEpochSecond(ZoneOffset.UTC))
                .sign(SIGN_ALGORITHM);
    }

    @Getter
    @AllArgsConstructor
    public static class JwtUser {
        private long userId;
        private LocalDateTime issuedAt;
        private LocalDateTime expiryTime;
        private String jti;

        private JwtUser() {
        }

        public static JwtUser of(long userId, LocalDateTime issuedAt, LocalDateTime expiryAt) {
            return new JwtUser(userId, issuedAt, expiryAt, UUID.randomUUID().toString());
        }
    }
}
