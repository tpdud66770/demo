## 📚 Book CRUD + Comment CRUD API
### Spring Boot · JPA · REST API · Postman Test

이 프로젝트는 Spring Boot + JPA 기반의 Book CRUD API와 
확장된 Comment CRUD 기능을 구현한 백엔드 과제입니다.

본인은 백엔드 파트를 맡았으며 , 일단 백엔드 부분에 대한 설명 진행 하겠습니다 . 

<p align="left">
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white&style=flat-square"/>
  <img src="https://img.shields.io/badge/Java-007396?logo=openjdk&logoColor=white&style=flat-square"/>
  <img src="https://img.shields.io/badge/JPA-59666C?logo=hibernate&logoColor=white&style=flat-square"/>
  <img src="https://img.shields.io/badge/Postman-FF6C37?logo=postman&logoColor=white&style=flat-square"/>
</p>


---

## 📌 **프로젝트 개요**

본 미션에서는 Spring Boot 기반으로 다음과 같은 기능을 구현하였습니다.

✔ Book CRUD 기능  

✔ Book DTO 분리 설계  

✔ RESTful API Controller 구성  

✔ 조회수 증가(viewCnt) 기능  

✔ 표지 이미지지 imgUrl 필드 추가 및 저장  

✔ Comment CRUD 기능 확장(등록/조회/수정/삭제)  

✔ Postman을 활용한 API 단위 테스트 완료  

---

## 📂 **프로젝트 구조**
```
src
└── main
    └── java
        └── com.example.demo
            ├── controller
            │   ├── BookController.java
            │   └── CommentController.java
            ├── domain
            │   ├── Book.java
            │   └── Comment.java
            ├── repository
            │   ├── BookRepository.java
            │   └── CommentRepository.java
            └── service
                ├── BookService.java
                ├── BookServiceImpl.java
                ├── CommentService.java
                └── CommentServiceImpl.java
```
---

## 📘 **Book 기능 상세**

### 📙 Book 엔티티

```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String content;
    private String author;
    
    private Long viewCnt = 0L;

    private LocalDate regTime;
    private LocalDate updateTime;

    private String imgUrl;
}
```
📌 imgUrl 기반 표지 이미지 관리 가능

📌 등록/수정 날짜 자동 저장

📌 조회수 자동 증가 기능 구현




### 📗 Book API 목록
| 기능       | Method | Endpoint                        |
| -------- | ------ | ------------------------------- |
| 도서 등록    | POST   | `/api/books/register`           |
| 도서 목록 조회 | GET    | `/api/books/list`               |
| 도서 상세 조회 | GET    | `/api/books/detail?id={bookId}` |
| 도서 수정    | PUT    | `/api/books/update`             |
| 도서 삭제    | DELETE | `/api/books/delete?id={bookId}` |




## 💬 **Comment 기능 상세**

### 💡 Comment 엔티티

```java
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;
    private String author;

    private LocalDate regTime;
    private LocalDate updateTime;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
```

📌 특정 Book에 종속되는 댓글(ManyToOne)

📌 수정/삭제 기능 포함

📌 Member 기능은 팀원이 완성 후 연동 예정


### 💬 Comment API 목록

| 기능       | Method | Endpoint                    |
| -------- | ------ | --------------------------- |
| 댓글 등록    | POST   | `/api/comments/{bookId}`    |
| 댓글 목록 조회 | GET    | `/api/comments/{bookId}`    |
| 댓글 수정    | PUT    | `/api/comments/{commentId}` |
| 댓글 삭제    | DELETE | `/api/comments/{commentId}` |

## 📡 **Postman API 응답 예시**

### 📘 Book API 응답

### ✅ ① 도서 등록 (POST/api/books/register)

### ✔ Request Body
```
{
  "title": "테스트 책",
  "content": "테스트 내용",
  "author": "홍길동",
  "imgUrl": "https://test-image.jpg"
}
```

### ✔ Response
```
{
  "bookId": 1,
  "title": "테스트 책",
  "content": "테스트 내용",
  "author": "홍길동",
  "viewCnt": 0,
  "regTime": "2025-12-05",
  "updateTime": "2025-12-05",
  "imgUrl": "https://test-image.jpg"
}
```

### ✅ ② 도서 상세 조회 (GET/api/books/detail?id=1)

### ✔ Response
```
{
  "bookId": 1,
  "title": "테스트 책",
  "content": "테스트 내용",
  "author": "홍길동",
  "viewCnt": 1,
  "regTime": "2025-12-05",
  "updateTime": "2025-12-05",
  "imgUrl": "https://test-image.jpg"
}
```

### ✅ ③ 도서 수정 (PUT/api/books/update)

### ✔ Request Body
```
{
  "bookId": 1,
  "title": "수정된 제목",
  "content": "수정된 내용",
  "author": "김철수",
  "imgUrl": "https://new-image.jpg"
}
```

### ✔ Response
```
{
  "bookId": 1,
  "title": "수정된 제목",
  "content": "수정된 내용",
  "author": "김철수",
  "viewCnt": 1,
  "regTime": "2025-12-05",
  "updateTime": "2025-12-05",
  "imgUrl": "https://new-image.jpg"
}
```
### ✅ ④ 도서 삭제 (DELETE /api/books/delete?id=1)

### ✔ Response
```[]
```

## 🧪 **Postman API 테스트 결과**

✔ Book 등록 성공

✔ Book 상세 조회 + 조회수 증가 확인

✔ Book 수정 후 imgUrl 정상 반영

✔ Comment 등록 성공 및 Book 연관관계 확인

✔ Comment 수정/삭제 정상 동작

✔ 삭제 후 GET 요청 시 빈 배열([]) 반환 확인

모든 API가 정상적으로 동작함을 검증 완료하였습니다 .


## 🏁 **구현 완료 사항 체크리스트**

| 항목                 | 상태 |
| ------------------ | -- |
| Book 엔티티 구현        | ✅  |
| Book Repository 생성 | ✅  |
| BookService & Impl | ✅  |
| BookController 구현  | ✅  |
| Book DTO 적용        | ✅  |
| Postman API 테스트    | ✅  |
| Comment CRUD 기능 확장 | ✅  |
