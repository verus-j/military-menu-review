# REST API 사용법

## DailyMealController
__XXXX년X월X주 식단표 반환(계정이 좋아요 누른 메뉴 아이디도 같이 반환)__
```
 URL : /daily-meal/list
 HTTP Method : GET
 headers : {"Authrization" : "JWT TOKEN"}
 param : year, month, week
 example : 
        const result = await axios.get("/daily-meal/list?year=2021&month=6&week=1", 
            {headers: {Authorization: "JWT TOKEN"}}); 
```

## MemberController
__사용자 로그인(JWT 토큰 값 반환)__
```
 URL : /member/login
 HTTP Method : POST
 headers : {"Content-Type": "application/json"}
 body : {username: "", password: ""}
 example : 
        const result = await axios.post("/member/login", 
            {"username": "username", "password": "password"}, 
            {headers: {"Content-Type": "application/json"}});
            
        result.headers.authorization // JWT 토큰 값     
```

__JWT 토큰 인증을 통한 사용자 정보 출력(아이디, 이름, 군번, 계정 타입)__
```
 URL : /member/info
 HTTP Method : GET
 headers : {"Authrization" : "JWT TOKEN"}
 example :
        await axios.get("/member/info", {headers: {Authorization: "JWT TOKEN"}});
```

__사용자 회원가입(군인의 경우 군번 필요)__
```
 URL : /member/join
 HTTP Method : POST
 headers : {"Content-Type": "application/json"}
 body : {username: "", password: "", name: "", militaryId: "", role: ""} // militaryId는 군인에게만 필요, role은 ["ADMIN", "SOLDIER", "NORMAL"] 중 선택
 example : 
        const result = await axios.post("/member/join", 
            {"username": "username", "password": "password, name: "name", militaryId: "militaryId", role: "role"}, 
            {headers: {"Content-Type": "application/json"}});
```
## MenuController
__전체 메뉴 정보 반환(메뉴 아이디, 메뉴 이름, 칼로리, 좋아요 수)__
```
 URL : /menu/list
 HTTP Method : GET
 headers : {"Authrization" : "JWT TOKEN"}
 example : 
        await axios.get("/menu/list", {headers: {Authrization: "JWT TOKEN"}});
```
__JWT 토큰을 사용한 사용자 인증 후 메뉴 좋아요 설정__
```
 URL : /menu/like
 HTTP Method : POST
 headers : {"Authrization" : "JWT TOKEN", "Content-Type": "application/json"}
 body: {mealId: 0, menuId: 0}
 example : 
        await axios.post("/menu/like",{mealId: 0, menuId: 0}, {headers: {"Authrization" : "JWT TOKEN", "Content-Type": "application/json"}});
```
__JWT 토큰을 사용한 사용자 인증 후 메뉴 좋아요 해제__
```
 URL : /menu/unlike
 HTTP Method : POST
 headers : {"Authrization" : "JWT TOKEN", "Content-Type": "application/json"}
 body: {mealId: 0, menuId: 0}
 example : 
        await axios.post("/menu/unlike",{mealId: 0, menuId: 0}, {headers: {"Authrization" : "JWT TOKEN", "Content-Type": "application/json"}});
```
## ReviewController
__JWT 토큰을 사용한 사용자 인증 후 리뷰 작성__
```
 URL : /review/new
 HTTP Method : POST
 headers : {"Authrization" : "JWT TOKEN", "Content-Type": "application/json"}
 body: {content: "content", mealId: 0}
 example : 
        await axios.post("/review/new",{content: "content", mealId: 0}, {headers: {"Authrization" : "JWT TOKEN", "Content-Type": "application/json"}});
```
__해당 식단(Meal)의 리뷰들 반환(현재 로그인된 사용자가 작성한 리뷰 mine 프로퍼티로 표시)__
```
 URL : /review/list
 HTTP Method : GET
 headers : {"Authrization" : "JWT TOKEN"}
 param : mealId, page, size // page는 0부터 시작, size는 값이 없는 경우 기본적으로 10
 example : 
        await axios.post("/review/list?mealId=612&page=0&size=5", {headers: {"Authrization" : "JWT TOKEN"}});
```