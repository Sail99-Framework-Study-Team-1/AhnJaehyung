# 4주차
2024년 8월 18일~2024년 8월 24일

## 한 일
- Spring Security 적용
- JWT 이용한 로그인 구현
- JPA join 관계와 N+1 문제

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
- JPA
  - Join 관계
    - DB의 OneToOne, OneToMany, ManyToOne, ManyToMany 등 관계를 지원한다.
  - N+1 문제
    - join관계가 존재하는 테이블의 정보를 가져올 때, 모든 row의 정보를 한 번에 가져오는 현상 발생 가능
    - 이 때, 쿼리가 과하게 수행되어 성능 문제 야기
    - 해결방법
      - Fetch Join
        - SQL에서 join문을 이용해 join table을 처음부터 가져오는 방법
        - SQL문을 직접 만들어줘야 함
      - Batch Size
        - N개의 row를 조회할 때, N번의 쿼리가 아닌 1번의 쿼리를 날려 해결하는 법
        - `where id=?`*N 대신 `where id in (?,?,...)`*1 수행
      - 그냥 Foreign key를 쓰지 말자
        - 실무에서 꽤 쓰인다고 카더라
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