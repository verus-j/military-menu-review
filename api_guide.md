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
#### HTTP 요청
```
GET /menus?size=3&page=0&sort=name%2CASC HTTP/1.1
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aWxndXI1MTMiLCJleHAiOjE2MzEyMTk4NTB9.S6C-2mIqG7YYCwwzNB8IDIF4NN0qjEwzH-A1uHRj085I8DQ1rqYx8aKr1eDf2wZ-sXztHWe4uWdX8wQECmm0PQ
Host: localhost:8080
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
Content-Length: 1392

{
  "_embedded" : {
    "menuResponseList" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 33,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/33"
        },
        "like" : {
          "href" : "http://localhost:8080/menus/33/likes"
        }
      }
    }, {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 34,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/34"
        },
        "like" : {
          "href" : "http://localhost:8080/menus/34/likes"
        }
      }
    }, {
      "name" : "c",
      "kcal" : 3.0,
      "like" : 0,
      "id" : 35,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/35"
        },
        "like" : {
          "href" : "http://localhost:8080/menus/35/likes"
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
### 메뉴 1건 조회
#### HTTP 요청
```
GET /menus/53 HTTP/1.1
Host: localhost:8080
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
Content-Length: 150

{
  "name" : "a",
  "kcal" : 1.0,
  "like" : 0,
  "id" : 53,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/menus/53"
    }
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
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Location: http://localhost:8080/menus/21/28
Content-Type: application/hal+json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 282

{
  "id" : 28,
  "memberId" : 29,
  "menuId" : 21,
  "dateTime" : "2021-09-10T04:47:21.94029",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/menus/21/likes/28"
    },
    "cancel-like" : {
      "href" : "http://localhost:8080/menus/21/cancel-like"
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
Content-Length: 97

{
  "_links" : {
    "like" : {
      "href" : "http://localhost:8080/menus/33/likes"
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
Content-Length: 2183

{
  "meals" : [ {
    "id" : 9,
    "date" : "2021-09-06",
    "mealType" : "BREAKFAST",
    "menus" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 17,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/17"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/9"
      }
    }
  }, {
    "id" : 10,
    "date" : "2021-09-06",
    "mealType" : "DINNER",
    "menus" : [ {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 18,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/18"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/10"
      }
    }
  }, {
    "id" : 11,
    "date" : "2021-09-06",
    "mealType" : "LUNCH",
    "menus" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 17,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/17"
        }
      }
    }, {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 18,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/18"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/11"
      }
    }
  }, {
    "id" : 12,
    "date" : "2021-09-07",
    "mealType" : "BREAKFAST",
    "menus" : [ {
      "name" : "a",
      "kcal" : 1.0,
      "like" : 0,
      "id" : 17,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/17"
        }
      }
    }, {
      "name" : "b",
      "kcal" : 2.0,
      "like" : 0,
      "id" : 18,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/menus/18"
        }
      }
    } ],
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/meals/12"
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
    }
  }
}
```
### 식단표 1건 조회
#### HTTP 요청
```
GET /meals/8 HTTP/1.1
Host: localhost:8080
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
Content-Length: 587

{
  "id" : 8,
  "date" : "2021-09-06",
  "mealType" : "BREAKFAST",
  "menus" : [ {
    "name" : "a",
    "kcal" : 1.0,
    "like" : 0,
    "id" : 15,
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/menus/15"
      }
    }
  }, {
    "name" : "b",
    "kcal" : 2.0,
    "like" : 0,
    "id" : 16,
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/menus/16"
      }
    }
  } ],
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/meals/8"
    },
    "meals" : {
      "href" : "/meals?year=2021&month=9&week=1"
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
HTTP/1.1 201 Created
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Location: http://localhost:8080/meals/29/reviews/81
Content-Type: application/hal+json
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 482

{
  "id" : 81,
  "mealId" : 29,
  "memberId" : 37,
  "content" : "contents",
  "created" : "2021-09-10T04:47:29.373809",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/meals/29/reviews/81"
    },
    "reviews" : {
      "href" : "http://localhost:8080/meals/29/reviews"
    },
    "update-review" : {
      "href" : "http://localhost:8080/meals/29/reviews/81"
    },
    "delete-review" : {
      "href" : "http://localhost:8080/meals/29/reviews/81"
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
Content-Length: 487

{
  "id" : 31,
  "mealId" : 20,
  "memberId" : 19,
  "content" : "update content",
  "created" : "2021-09-10T04:47:27.38265",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/meals/20/reviews/31"
    },
    "reviews" : {
      "href" : "http://localhost:8080/meals/20/reviews"
    },
    "update-review" : {
      "href" : "http://localhost:8080/meals/20/reviews/31"
    },
    "delete-review" : {
      "href" : "http://localhost:8080/meals/20/reviews/31"
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
Content-Length: 189

{
  "_links" : {
    "reviews" : {
      "href" : "http://localhost:8080/meals/21/reviews"
    },
    "create-review" : {
      "href" : "http://localhost:8080/meals/21/reviews"
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
Content-Length: 1616

{
  "_embedded" : {
    "reviewResponseList" : [ {
      "id" : 1,
      "mealId" : 17,
      "memberId" : 13,
      "content" : "content1",
      "created" : "2021-09-10T04:47:26.704253",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/meals/17/reviews/1"
        },
        "reviews" : {
          "href" : "http://localhost:8080/meals/17/reviews"
        }
      }
    }, {
      "id" : 2,
      "mealId" : 17,
      "memberId" : 14,
      "content" : "content2",
      "created" : "2021-09-10T04:47:26.706488",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/meals/17/reviews/2"
        },
        "reviews" : {
          "href" : "http://localhost:8080/meals/17/reviews"
        }
      }
    }, {
      "id" : 3,
      "mealId" : 17,
      "memberId" : 14,
      "content" : "content3",
      "created" : "2021-09-10T04:47:26.707487",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/meals/17/reviews/3"
        },
        "reviews" : {
          "href" : "http://localhost:8080/meals/17/reviews"
        }
      }
    } ]
  },
  "_links" : {
    "first" : {
      "href" : "http://localhost:8080/meals/17/reviews?page=0&size=3"
    },
    "self" : {
      "href" : "http://localhost:8080/meals/17/reviews?page=0&size=3"
    },
    "next" : {
      "href" : "http://localhost:8080/meals/17/reviews?page=1&size=3"
    },
    "last" : {
      "href" : "http://localhost:8080/meals/17/reviews?page=3&size=3"
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
### 리뷰 1건 조회
#### HTTP 요청
```
GET /meals/18/reviews/11 HTTP/1.1
Host: localhost:8080
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
Content-Length: 302

{
  "id" : 11,
  "mealId" : 18,
  "memberId" : 15,
  "content" : "content1",
  "created" : "2021-09-10T04:47:26.920559",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/meals/18/reviews/11"
    },
    "reviews" : {
      "href" : "http://localhost:8080/meals/18/reviews"
    }
  }
}
```