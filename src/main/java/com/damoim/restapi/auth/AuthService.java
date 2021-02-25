package com.damoim.restapi.auth;

import com.damoim.restapi.member.dao.MemberRepository;
import com.damoim.restapi.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;


    public String naverCallback(String code) {
        GetTokenResponse response = fetchAccessToken(code);
        GetUserInfoResponse.UserInfo userInfo = fetchUserInfoFromNaver(response.access_token);

        Member member = Member.builder()
                .id(userInfo.nickname)
                .name(userInfo.name)
                .email(userInfo.email)
                .register("OAUTH")
                .build();

        member = memberRepository.save(member);

        return jwtService.encode(JwtService.JwtUser.of(member.getNo(), LocalDateTime.now(), LocalDateTime.now().plusDays(30)));
    }

    private GetTokenResponse fetchAccessToken(String code) {
        return restTemplate.exchange(getAccessToken(code), HttpMethod.GET, null, GetTokenResponse.class).getBody();
    }

    private GetUserInfoResponse.UserInfo fetchUserInfoFromNaver(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        HttpEntity<?> entity = new HttpEntity<>(header);
        ResponseEntity<GetUserInfoResponse> rr = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, entity, GetUserInfoResponse.class);
        return Objects.requireNonNull(rr.getBody()).response;
    }

    private String getAccessToken(String code) {
        // FIXME secret 환경변수로 옮겨야 함
        return "https://nid.naver.com/oauth2.0/token?client_id=" + "qFAw79F6W2qVUb7lPG3F" + "&client_secret=" + "xvMLD0uRNK" + "&grant_type=authorization_code&state=" + 123 + "&code=" + code;
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
            private String id;
            private String nickname;
            private String email;
            private String name;
        }
    }
}
