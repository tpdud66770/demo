# 창작 도서 관리 시스템 "걷기가 서재" (백엔드)
> **프론트엔드 깃허브 링크** : [https://github.com/bookfront/bookmuseum]

## 1. 프로젝트 소개
걷기가 서재의 “작가의 산책” 서비스는 누구나 작가가 되어 자유롭게 글을 집필하고 공개할
수 있는 창작 플랫폼 입니다. 본 깃허브 프로젝트는 걷기가 서재의 로그인, 도서등록, 이미지 생성 등의 서비스를 제공하는 서버단을 담당하고 있습니다 .

## 2. 주요 기능
- 회원가입/로그인/ 로그아웃
- 창작 도서 생성/수정/삭제
- 전체 도서 조회 / 인기 도서 조회 서비스
- 마이페이지에서 본인등록도서 및 좋아요 누른 도서 목록 조회
- 댓글 / 좋아요 
- AI 책 표지 생성 / 수정 
   
## 3. 기술 스택
- SpringBoots
- RESTful API
- Spring Security
- Cookie & JWT
- H2-console
- JPA Repository

## 4. 시스템 아키텍처 구조
BookMuseum 백엔드는 JWT 기반 인증 시스템, Spring Security 필터 체인 ,

그리고 React 프론트엔드 + OpenAI 이미지 생성 API와 연동되는 구조로 설계되었습니다 . 
### 📌 전체 요청 흐름
```
① [브라우저] 회원가입/로그인 요청 
② [React Front] 로그인 성공 → 서버가 JWT 쿠키(accessToken) 발급 
③ [Spring Boot Backend] 보호된 API 요청 시, JwtFilter가 accessToken 자동 검증 및 [DB(H2)] 로 이동
```

### 📌 이미지 생성 흐름 (AI 책 표지)
```
사용자 → 프론트에서 OpenAI 이미지 생성 요청
      → 생성된 imageURL을 서버에 PUT(/api/books/{id}/cover-url)으로 저장
      → 책 상세 페이지 및 목록에서 저장된 이미지 자동 반영
```

### 📌 Security Filter 구조
```
요청 → SecurityConfig → JwtFilter → Controller → Service → DB
```

SecurityConfig의 인증 제외 API:

- /api/member/**

- /api/main/**

- /h2-console/**

그 외 /api/**는 모두 JWT 인증 필요.
 
## 5. 프로젝트 디렉터리
```
src
└── main
    └── java
        └── com.example.demo
            ├── config
            │   └── WebConfig.java
            ├── configuration
            │   └── SecurityConfig.java
            ├── controller
            │   ├── BookController.java
            │   ├── CommentController.java
            │   ├── MemberController.java
            │   └── MypageController.java
            ├── domain
            │   ├── Book.java
            │   ├── Comment.java
            │   ├── Likes.java
            │   └── Member.java
            ├── dto
            ├── exception
            │   └── GlobalExceptionHandler.java
            ├── repository
            ├── security
            └── service
```
## 7. 주요 API 명세 
### Book API 목록
| 기능              | Method     | Endpoint                        | 설명                            |
| --------------- | ---------- | ------------------------------- | ----------------------------- |
| 도서 등록           | **POST**   | `/api/books`                    | Book 생성                       |
| 도서 전체 목록 조회     | **GET**    | `/api/books`                    | 모든 도서 조회                      |
| 인기 도서 조회        | **GET**    | `/api/books/hot`                | 조회수 기준 인기 도서                  |
| 도서 상세 조회        | **GET**    | `/api/books/{bookId}`           | 특정 도서 조회 + viewCnt 증가         |
| 도서 수정           | **PUT**    | `/api/books/{bookId}`           | 해당 bookId 수정                  |
| 도서 삭제           | **DELETE** | `/api/mypage/{bookId}`          | 마이페이지에서 등록자가 자신의 도서 삭제        |
| 좋아요 / 좋아요 취소    | **PATCH**  | `/api/books/{bookId}`           | 로그인 사용자만 가능, liked/unliked 반환 |
| 책 커버 이미지 URL 저장 | **PUT**    | `/api/books/{bookId}/cover-url` | 생성된 이미지 URL 저장                |

### Comment API 목록
| 기능       | Method     | Endpoint                       | 설명           |
| -------- | ---------- | ------------------------------ | ------------ |
| 댓글 등록    | **POST**   | `/api/books/{bookId}/comments` | 해당 도서에 댓글 추가 |
| 댓글 목록 조회 | **GET**    | `/api/books/{bookId}/comments` | 해당 도서의 모든 댓글 |
| 댓글 수정    | **PUT**    | `/api/comments/{commentId}`    | 댓글 내용 수정     |
| 댓글 삭제    | **DELETE** | `/api/comments/{commentId}`    | 본인 댓글만 삭제 가능 |

### Member API 목록
| 기능   | Method   | Endpoint            | 설명         |
| ---- | -------- | ------------------- | ---------- |
| 회원가입 | **POST** | `/api/member`       | 새로운 사용자 생성 |
| 로그인  | **POST** | `/api/member/login` | JWT 토큰 발급  |

### MyPageAPI 목록
| 기능               | Method     | Endpoint               | 설명                 |
| ---------------- | ---------- | ---------------------- | ------------------ |
| 내가 등록한 도서 조회     | **GET**    | `/api/mypage`          | 로그인한 사용자가 등록한 책 목록 |
| 내가 좋아요한 도서 목록 조회 | **GET**    | `/api/mypage/liked`    | 좋아요 누른 책 목록        |
| 도서 삭제            | **DELETE** | `/api/mypage/{bookId}` | 내가 등록한 책만 삭제 가능    |


## 8. 프로젝트 설계 문서 링크
프로젝트 전반의 흐름, ERD 구조, API 명세서, 협업 규칙 등이 담겨 있습니다.
```
📄 프로젝트 흐름(notion)
```
🔗 https://www.notion.so/4-20-_-2bf1779ea17e8055b273d610729db2e2

```
📄 ERD 구조와 API 명세서
```
🔗 https://drive.google.com/file/d/1HQs4t6HzsPvZyDS_ad77loR5L-eWW3dj/view?usp=sharing

🔗 https://docs.google.com/spreadsheets/d/131vSMyB1M9gsBOHBgG41y6cmInuIWBZRxlcFOYFgs3s/edit?usp=sharing
```
📄 UI 설계(Figma)
```
🔗 https://www.figma.com/design/zsT7VhkMluKt9uq4AteEPI/BOOK-UI


## 9. 개발 중 배운 점 & 개선점
### 🔍 1) JWT + Cookie 기반 인증 설계 경험

- AccessToken을 HTTP-Only 쿠키로 저장하여 XSS 공격을 원천 차단.

- React Fetch 요청에서 credentials: "include"가 필수임을 명확하게 이해.

- 인증 필터를 거쳐야만 API가 동작하도록 구성해, 보안성을 크게 높임.

### 🔍 2) Spring Security 필터 체인의 실제 동작 이해

- 인증 예외 처리(JwtAuthenticationEntryPoint)

- 요청별 인증 제외 경로(/api/main/** , /api/member/** , /h2-console/** )

- JwtFilter가 실행되는 정확한 시점 파악

### 🔍 3) 협업 시 API 명세 정합성의 중요성


- 프론트와 엔드포인트 불일치 시 즉시 오류 발생

- ENDPONIT 통합 및 README로 표준화하여 문제 해결

- 로그인 전/후 메인화면 로직 충돌 → MainController로 분리하여 해결


### 🔍 4) 개선해야 할 점


- 이미지 생성(OpenAI) 기능도 백엔드에서 지원한다면 더 완성도 있는 구조 가능

- Error Code 표준화 필요 (현재는 단순 message 기반)

- Validation 추가 필요 (title, content 길이 제한 등)

- 통합 테스트(JUnit) 도입 미완료 → 추후 CI/CD 환경에서 필요




