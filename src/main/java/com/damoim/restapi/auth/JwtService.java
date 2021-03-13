package com.damoim.restapi.auth;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@Service
@RequiredArgsConstructor
public class JwtService {

	private final AuthProperties authProperties;
	private static final String EXPIRY_TIME = "expiryTime";
	private static final String ISSUED_AT = "issuedAt";
	private static final String ID = "id";

	public Algorithm getAuthAlgorithm() {
		return Algorithm.HMAC256(authProperties.getSignSecret());
	}

	public JwtUser decode(String jwtToken) {
		try {
			DecodedJWT s = JWT.decode(jwtToken);
			JWTVerifier verifier = JWT.require(getAuthAlgorithm()).build();

			verifier.verify(s);

			if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) > s.getClaim(EXPIRY_TIME).asLong()) {
				throw new BadCredentialsException("expired token");
			}

			return JwtUser.of(s.getClaim(ID).asLong(),
				LocalDateTime.ofEpochSecond(s.getClaim(ISSUED_AT).asLong(), 0, ZoneOffset.UTC),
				LocalDateTime.ofEpochSecond(s.getClaim(EXPIRY_TIME).asLong(), 0, ZoneOffset.UTC));
		} catch (JWTDecodeException e) {
			throw new BadCredentialsException("Invalid access token");
		}
	}

	public String encode(JwtUser user) {
		return JWT.create().withJWTId(user.jti)
			.withClaim(ID, user.userId)
			.withClaim(ISSUED_AT, user.issuedAt.toEpochSecond(ZoneOffset.UTC))
			.withClaim(EXPIRY_TIME, user.expiryTime.toEpochSecond(ZoneOffset.UTC))
			.sign(getAuthAlgorithm());
	}

	@Getter
	@AllArgsConstructor
	public static class JwtUser {
		private long userId;
		private LocalDateTime issuedAt;
		private LocalDateTime expiryTime;
		private String jti;

		public static JwtUser of(long userId, LocalDateTime issuedAt, LocalDateTime expiryAt) {
			return new JwtUser(userId, issuedAt, expiryAt, UUID.randomUUID().toString());
		}
	}
}
