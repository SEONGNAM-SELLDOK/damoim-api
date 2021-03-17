package com.damoim.restapi.auth;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.damoim.restapi.member.dao.MemberRepository;
import com.damoim.restapi.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author dodo45133@gmail.com
 * @since 2021. 03. 04.
 */

@Service
@RequiredArgsConstructor
public class AuthService {

	private final RestTemplate restTemplate;
	private final JwtService jwtService;
	private final MemberRepository memberRepository;
	private final AuthProperties authProperties;

	@Transactional
	public String naverCallback(String code) {
		GetTokenResponse response = fetchAccessToken(code);
		GetUserInfoResponse.UserInfo userInfo = fetchUserInfoFromNaver(response.access_token);

		Member member = createOrUpdateMember(userInfo);

		return jwtService.encode(
			JwtService.JwtUser.of(member.getNo(), LocalDateTime.now(), LocalDateTime.now().plusDays(30)));
	}

	private Member createOrUpdateMember(GetUserInfoResponse.UserInfo userInfo) {
		Member member = memberRepository.findByEmail(userInfo.email);
		if (member != null) {
			member.setId(userInfo.email);
			member.setName(userInfo.name);
		} else {
			Member newMember = Member.builder()
				.id(userInfo.email)
				.name(userInfo.name)
				.email(userInfo.email)
				.register("OAUTH")
				.build();
			member = memberRepository.save(newMember);
		}
		return member;
	}

	private GetTokenResponse fetchAccessToken(String code) throws SecurityException {
		ResponseEntity<GetTokenResponse> response = null;
		response = restTemplate.getForEntity(getAccessToken(code), GetTokenResponse.class);
		if (response == null) {
			throw new SecurityException("error naver callback");
		}
		return response.getBody();
	}

	private String getAccessToken(String code) {
		return "https://nid.naver.com/oauth2.0/token?client_id=" + authProperties.getClientId() + "&client_secret="
			+ authProperties.getSecret()
			+ "&grant_type=authorization_code&state=123&code=" + code;
	}

	private GetUserInfoResponse.UserInfo fetchUserInfoFromNaver(String accessToken) {
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + accessToken);
		HttpEntity<?> entity = new HttpEntity<>(header);
		ResponseEntity<GetUserInfoResponse> response = restTemplate.exchange("https://openapi.naver.com/v1/nid/me",
			HttpMethod.GET, entity, GetUserInfoResponse.class);
		return Objects.requireNonNull(response.getBody()).response;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	static class GetTokenResponse {
		private String access_token;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	static class GetUserInfoResponse {
		private String resultcode;
		private String message;
		private UserInfo response;

		@Setter
		@Getter
		@NoArgsConstructor
		static class UserInfo {
			private String email;
			private String name;
		}
	}
}
