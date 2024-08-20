# 4주차
2024년 8월 18일~2024년 8월 24일

## 한 일
- Spring Security 적용
- JWT 이용한 로그인 구현

## 배운 것
- Spring Security
  - Spring으로 들어온 요청은 프록시 패턴을 통해 여러 단계를 거쳐 Controller로 전달
    - Security Filter Chain의 종류
      - LoginFilter(UsernamePasswordAuthenticationFilter)
        - 로그인 과정을 처리
        - username과 password를 검증 후, jwt를 생성하여 header에 추가해주는 응답을 발송하도록 구현
      - JWTFilter(OncePerRequestFilter)
        - 세션 혹은 JWT를 검증한다.
        - 검증된 principal, credential, authorities는 SecurityContext에 저장한다
      - SecurityContextFilter
        - SecurityContext가 존재하는지 검사한다.
        - Authentication이 필요한 페이지에서 SecurityContext가 존재하지 않으면, 요청을 서버로 보내지 않는다. 
- Guard Clause
  - If문에 관한 클린코드 기법의 일종
  - 여러 조건을 만족해야 로직이 수행될 경우, 아래와 같이 indentation을 줄여 가독성 향상
    - Without guard clause
      - ```java
        if (A) {               
            if (B) {
                if (C) {
                    // ...execution
                }
            }
        }
        ```
    - With guard clause
      - ```java
        if (!A) {               
            return;
        }
        if (!B) {               
            return;
        }
        if (!C) {               
            return;
        }
        // ...execution
        ```