# 군 식단 리뷰 RESTful API

## 개요
국방부 공공 데이터 포털에서 군 식단 정보를 가져와 군 식단 리뷰를 위한 RESTful API를 개발했습니다. 아래의 두 영상을 참고하여 만들었고, Self-Descriptive Message와 HATEOAS(Hypermedia as the engine of application state)를 만족하는 REST API를 만들려고 노력했습니다.<br>

* [그런 REST API로 괜찮은가?](https://www.youtube.com/watch?v=RP_f5dMoHFc&t=1s)
* [스프링 기반 REST API 개발](https://www.inflearn.com/course/spring_rest-api/dashboard)

## RESTful 가이드

### 유저 회원가입
#### HTTP 요청
```
POST /join HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 99
Host: localhost:8080

{
  "username" : "wilgur513",
  "password" : "pass",
  "name" : "정진혁",
  "type" : "SOLDIER"
}
```
#### HTTP 응답
```
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/hal+json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 175

{
  "id" : 5,
  "username" : "wilgur513",
  "name" : "정진혁",
  "type" : "SOLDIER",
  "_links" : {
    "login" : {
      "href" : "http://localhost:8080/login"
    }
  }
}
```
### 유저 로그인
#### HTTP 요청
```
POST /login HTTP/1.1
Content-Type: application/json;charset=UTF-8
Content-Length: 53
Host: localhost:8080

{
  "username" : "wilgur513",
  "password" : "pass"
}
```
#### HTTP 응답
```
HTTP/1.1 200 OK
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NDV9.n67cORyiiPkqokapC_RXy7P7u6pz0MoNuI9OEY7Zj1bfv3xfTqA0O9pJ9lGwruHtm-ufxrVtf7f9Sb72IZ_2HA
Content-Type: application/json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 88

{
  "id" : 6,
  "username" : "wilgur513",
  "name" : "정진혁",
  "type" : "SOLDIER"
}
```
## 메뉴
### 메뉴 조회
```
```
