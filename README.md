# 군 식단 리뷰 RESTful API

## 개요
국방부 공공 데이터 포털에서 군 식단 정보를 가져와 군 식단 리뷰를 위한 RESTful API를 개발했습니다. 식단표를 가져올 수 있고, JWT 토큰을 통해 계정 인증 후 식단별 리뷰와 메뉴별 좋아요를 설정할 수 있습니다. 아래의 두 영상을 참고하여 만들었고, Self-Descriptive Message와 HATEOAS(Hypermedia as the engine of application state)를 만족하는 REST API를 만들려고 노력했습니다.

* [그런 REST API로 괜찮은가?](https://www.youtube.com/watch?v=RP_f5dMoHFc&t=1s)
* [스프링 기반 REST API 개발](https://www.inflearn.com/course/spring_rest-api/dashboard)

## 사용 기술
* Java8
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring HATEOAS
* Spring Rest Docs
* JPA

## 실행 방법
```
    git clone https://github.com/wilgur513/military-menu-review.git
    cd military-menu-review
    mvn package // Spring Rest Docs 초기화
    mvn spring-boot:run // Spring Application 실행
```

## RESTful API 문서
애플리케이션 실행 후 localhost:8080/docs/index.html로 접속 시 Spring Rest Docs를 사용해 만든 문서를 확인할 수 있습니다.
