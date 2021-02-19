## 프로젝트
- 다모임 REST API

## 개발환경
- jdk 11
- spring boot 2.4
- spring 5.x
- JPA
- hibernate
- h2 db(추후 mysql 전환 예정) 

## 프로젝트 범위
- 사이트 게시글
- 모임
- 커뮤니티 게시글
- 도서리뷰
- 강의
- 구인구직
- 중고거래

## 참고 사이트
- OKKY(http://okky.kr)
- WANTED(https://www.wanted.co.kr)
- JOB PLANET(http://jobplanet.co.kr)
- ROCKET PUNCH(https://www.rocketpunch.com)
- DANGGUN MARKET(https://www.daangn.com)


## 실행 방법

### 애플리케이션 실행

```
gradlew bootrun
```

## 확인 방법
### http Plugin 활용
```
### 회원 생성
POST http://localhost:9090/members
Content-Type: application/json

{
  "id" : "incheol@naver.com",
  "name" : "정인철",
  "pwd" : "password",
  "profilePicUrl" : "temp"
}

> {%
client.test("Request executed successfully", function() {
    client.log("response.body" + response.status)
    client.assert(response.status === 201, "Response status is not 201, it is " + response.status);
});
%}

```

### 데이터베이스 콘솔 활용(localhost:9090/h2-db)
![image](https://user-images.githubusercontent.com/2491418/99860795-7b358480-2bd7-11eb-916b-ffbd9b665ce8.png)

### 클라우드 환경 URL
- https://www.ncloud.com/nsa/damoim

### CI/CD 
- 깃헙 빌드 검증
- 소나큐브 클라우드 코드 컨벤션 적용
- 네이버 클라우드 파이프라인 적용(빌드 / 배포)

### 모니터링
- 추후 적용

### 웹 서버
- nginx

### SITE INFO
- Front : http://118.67.130.14/
- BackEnd : http://118.67.130.14:8080
