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
    mvn package 
    mvn spring-boot:run 
```

## RESTful API 문서
애플리케이션 실행 후 localhost:8080/docs/index.html로 접속 시 Spring Rest Docs를 사용해 만든 문서를 확인할 수 있습니다.

### 유저 회원가입
#### HTTP 요청
```
POST /join HTTP/1.1
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
{
  "id" : 37,
  "username" : "wilgur513",
  "name" : "정진혁",
  "type" : "SOLDIER",
  "_links" : {
    "login" : {
      "href" : "http://localhost:8080/login"
    },
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resources-join-member"
    }
  }
}
```
### 유저 로그인
#### HTTP 요청
```
POST /login HTTP/1.1
Host: localhost:8080

{
  "username" : "wilgur513",
  "password" : "pass"
}
```
#### HTTP 응답
```
{
  "id" : 38,
  "username" : "wilgur513",
  "name" : "정진혁",
  "type" : "SOLDIER",
  "_links" : {
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resources-login-member"
    }
  }
}
```
## 메뉴
### 메뉴 조회
#### HTTP 요청
```
GET /menus?size=3&page=0&sort=name%2CASC HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NTB9.S6C-2mIqG7YYCwwzNB8IDIF4NN0qjEwzH-A1uHRj085I8DQ1rqYx8aKr1eDf2wZ-sXztHWe4uWdX8wQECmm0PQ
Host: localhost:8080
```
#### HTTP 응답
```
{
  "_embedded" : {
    "menuResponseList" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 47,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/47"
        },
        "like" : {
          "href" : "http://localhost:8080/menus/47/likes"
        }
      }
    }, {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 48,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/48"
        },
        "like" : {
          "href" : "http://localhost:8080/menus/48/likes"
        }
      }
    }, {
      "name" : "c",
      "kcal" : 3.0,
      "like" : 0,
      "id" : 49,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/49"
        },
        "like" : {
          "href" : "http://localhost:8080/menus/49/likes"
        }
      }
    } ]
  },
  "_links" : {
    "first" : {
      "href" : "http://localhost:8080/menus?page=0&size=3&sort=name,asc"
    },
    "self" : {
      "href" : "http://localhost:8080/menus?page=0&size=3&sort=name,asc"
    },
    "next" : {
      "href" : "http://localhost:8080/menus?page=1&size=3&sort=name,asc"
    },
    "last" : {
      "href" : "http://localhost:8080/menus?page=3&size=3&sort=name,asc"
    },
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resources-query-menus"
    }
  },
  "page" : {
    "size" : 3,
    "totalElements" : 10,
    "totalPages" : 4,
    "number" : 0
  }
}
```

### 메뉴 좋아요 설정
#### HTTP 요청
```
POST /menus/21/likes HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NDF9.-Cqsdk1q2ZxSmUzv_cQ_V6bSJg3ebZD_Rk0G4EnUFJ14TryoMQxS34lMTlQe82ZN_hiIlCYLNozq9sJVo9p91g
Host: localhost:8080
```
#### HTTP 응답
```
{
  "id" : 28,
  "memberId" : 29,
  "menuId" : 21,
  "dateTime" : "2021-10-16T18:27:02.644",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/menus/21/likes/28"
    },
    "cancel-like" : {
      "href" : "http://localhost:8080/menus/21/cancel-like"
    },
    "profile" : {
      "href" : "/docs/index.html#resources-like-menu"
    }
  }
}
```
### 메뉴 좋아요 해제 설정
#### HTTP 요청
```
DELETE /menus/33/cancel-like HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NDJ9.gq-fpDR6FamTFKneWQZFk7mcmZYLL1PTVQC5sZn3-6kQ9DQ0H84LTd2Je_5pyckdH_orOnpiP-UgMSy4bh4uyw
Host: localhost:8080
```
#### HTTP 응답
```
{
  "_links" : {
    "like" : {
      "href" : "http://localhost:8080/menus/33/likes"
    },
    "profile" : {
      "href" : "/docs/index.html#resources-cancel-like"
    }
  }
}
```
## 식단표
### 식단표 주별 조회
#### HTTP 요청
```
GET /meals?year=2021&month=9&week=1 HTTP/1.1
Host: localhost:8080
```
#### HTTP 응답
```
{
  "meals" : [ {
    "id" : 17,
    "date" : "2021-09-06",
    "mealType" : "BREAKFAST",
    "menus" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 70,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/70"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/17"
      }
    }
  }, {
    "id" : 18,
    "date" : "2021-09-06",
    "mealType" : "DINNER",
    "menus" : [ {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 71,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/71"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/18"
      }
    }
  }, {
    "id" : 19,
    "date" : "2021-09-06",
    "mealType" : "LUNCH",
    "menus" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 70,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/70"
        }
      }
    }, {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 71,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/71"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/19"
      }
    }
  }, {
    "id" : 20,
    "date" : "2021-09-07",
    "mealType" : "BREAKFAST",
    "menus" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 70,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/70"
        }
      }
    }, {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 71,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/71"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/20"
      }
    }
  } ],
  "_links" : {
    "prev-week" : {
      "href" : "/meals?year=2021&month=8&week=5"
    },
    "next-week" : {
      "href" : "/meals?year=2021&month=9&week=2"
    },
    "self" : {
      "href" : "/meals?year=2021&month=9&week=1"
    },
    "profile" : {
      "href" : "/docs/index.html#resources-query-meals"
    }
  }
}
```
## 리뷰
### 리뷰 작성
#### HTTP 요청
```
POST /meals/29/reviews HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NDl9.flrxKIDYiylxCREOjV3TsvXx4-jt_Ffj_ZYx9yTHJ47fBgkMfdKQoZ86VTlg2PIPn4JfdCUEFkiQBNu-UTW0gQ
Content-Length: 28
Host: localhost:8080

{
  "content" : "contents"
}
```
#### HTTP 응답
```
{
  "id" : 81,
  "mealId" : 13,
  "memberId" : 63,
  "content" : "contents",
  "created" : "2021-10-16T18:27:09.927",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/meals/13/reviews/81"
    },
    "reviews" : {
      "href" : "http://localhost:8080/meals/13/reviews"
    },
    "update-review" : {
      "href" : "http://localhost:8080/meals/13/reviews/81"
    },
    "delete-review" : {
      "href" : "http://localhost:8080/meals/13/reviews/81"
    },
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resouces-create-review"
    }
  }
}
```
### 리뷰 수정
#### HTTP 요청
```
PUT /meals/20/reviews/31 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NDd9.rTj054Gi9GynvmPQMGwFWaFTHjcRu7x8NxAHFbgohk6S6DfubLI-GpaB813cDYR9Y0CB-synctNXERTUE2K8tQ
Content-Length: 34
Host: localhost:8080

{
  "content" : "update content"
}
```
#### HTTP 응답
```
{
  "id" : 31,
  "mealId" : 4,
  "memberId" : 45,
  "content" : "update content",
  "created" : "2021-10-16T18:27:06.936",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/meals/4/reviews/31"
    },
    "reviews" : {
      "href" : "http://localhost:8080/meals/4/reviews"
    },
    "update-review" : {
      "href" : "http://localhost:8080/meals/4/reviews/31"
    },
    "delete-review" : {
      "href" : "http://localhost:8080/meals/4/reviews/31"
    },
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resources-update-review"
    }
  }
}
```
### 리뷰 삭제
#### HTTP 요청
```
DELETE /meals/21/reviews/41 HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NDd9.rTj054Gi9GynvmPQMGwFWaFTHjcRu7x8NxAHFbgohk6S6DfubLI-GpaB813cDYR9Y0CB-synctNXERTUE2K8tQ
Host: localhost:8080
```
#### HTTP 응답
```
{
  "_links" : {
    "reviews" : {
      "href" : "http://localhost:8080/meals/5/reviews"
    },
    "create-review" : {
      "href" : "http://localhost:8080/meals/5/reviews"
    },
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resources-delete-review"
    }
  }
}

```
### 리뷰 조회
#### HTTP 요청
```
GET /meals/17/reviews?size=3&page=0 HTTP/1.1
Host: localhost:8080
```
#### HTTP 응답
```
{
  "_embedded" : {
    "reviewResponseList" : [ {
      "id" : 1,
      "mealId" : 1,
      "memberId" : 39,
      "content" : "content1",
      "created" : "2021-10-16T18:27:05.919",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/meals/1/reviews/1"
        },
        "reviews" : {
          "href" : "http://localhost:8080/meals/1/reviews"
        }
      }
    }, {
      "id" : 2,
      "mealId" : 1,
      "memberId" : 40,
      "content" : "content2",
      "created" : "2021-10-16T18:27:05.921",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/meals/1/reviews/2"
        },
        "reviews" : {
          "href" : "http://localhost:8080/meals/1/reviews"
        }
      }
    }, {
      "id" : 3,
      "mealId" : 1,
      "memberId" : 40,
      "content" : "content3",
      "created" : "2021-10-16T18:27:05.922",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/meals/1/reviews/3"
        },
        "reviews" : {
          "href" : "http://localhost:8080/meals/1/reviews"
        }
      }
    } ]
  },
  "_links" : {
    "first" : {
      "href" : "http://localhost:8080/meals/1/reviews?page=0&size=3"
    },
    "self" : {
      "href" : "http://localhost:8080/meals/1/reviews?page=0&size=3"
    },
    "next" : {
      "href" : "http://localhost:8080/meals/1/reviews?page=1&size=3"
    },
    "last" : {
      "href" : "http://localhost:8080/meals/1/reviews?page=3&size=3"
    },
    "profile" : {
      "href" : "http://localhost:8080/docs/index.html#resources-query-reviews"
    }
  },
  "page" : {
    "size" : 3,
    "totalElements" : 10,
    "totalPages" : 4,
    "number" : 0
  }
}
```
