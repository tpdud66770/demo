--------------------------------------------------------
-- MEMBER 초기 데이터
--------------------------------------------------------
INSERT INTO member (id, login_id, pass, name)
VALUES (1, 'admin', '1234', '관리자');

INSERT INTO member (id, login_id, pass, name)
VALUES (2, 'user01', '1234', '사용자1');

INSERT INTO member (id, login_id, pass, name)
VALUES (3, 'user02', '1234', '사용자2');


--------------------------------------------------------
-- BOOK 초기 데이터
--------------------------------------------------------
INSERT INTO book (book_id, view_cnt, author, title, content, reg_time, update_time, img_url)
VALUES (1, 10, '무라카미 하루키','상실의 시대','절친의 죽음으로 충격을 받은 대학생 와타나베가 주인공으로, 그와 죽은 친구의 연인 나오코의 관계를 중심으로 사랑, 상실, 성장을 그리는 소설', '2025-01-01', '2025-01-02', '/img/book1.jpg');

INSERT INTO book (book_id, view_cnt, author, title, content, reg_time, update_time, img_url)
VALUES (2, 45, '조지 오웰', '1984',
        '전체주의 감시 체제 아래에서 개개인의 사상과 자유가 완전히 통제되는 디스토피아 사회를 그린 작품으로, 주인공 윈스턴이 감시와 조작 속에서 인간의 존엄을 지키려는 치열한 저항을 보여준다',
        '2025-01-03', '2025-01-04', '/img/book2.jpg');

INSERT INTO book (book_id, view_cnt, author, title, content, reg_time, update_time, img_url)
VALUES (3, 12, '파울로 코엘료', '연금술사',
        '양치기 소년 산티아고가 자신의 운명과 꿈을 찾아가는 여정 속에서 우주의 징표와 영혼의 언어를 이해하며 진정한 행복을 깨닫는 영적 탐험기',
        '2025-01-05', '2025-01-06', '/img/book3.jpg');

INSERT INTO book (book_id, view_cnt, author, title, content, reg_time, update_time, img_url)
VALUES (4, 31, '도스토예프스키', '죄와 벌',
        '가난한 청년 라스콜리니코프가 범죄를 저지른 후 겪는 죄책감, 고뇌, 구원 의식을 통해 인간 내면의 도덕성과 심리적 균열을 극한까지 밀어붙이는 심리 소설',
        '2025-01-07', '2025-01-08', '/img/book4.jpg');

--------------------------------------------------------
-- COMMENT 초기 데이터
--------------------------------------------------------
INSERT INTO comment (comment_id, book_id, member_id, content, author, reg_time, update_time)
VALUES (1,1,1, '재밌게 읽었습니다!', 'user01', '2025-01-02', '2025-01-02');
INSERT INTO comment (comment_id, book_id, member_id, content, author, reg_time, update_time)
VALUES (2,2,1, '재밌게 읽었습니다!', 'user01', '2025-01-02', '2025-01-02');
INSERT INTO comment (comment_id, book_id, member_id, content, author, reg_time, update_time)
VALUES (3,3,1, '재밌게 읽었습니다!', 'user01', '2025-01-02', '2025-01-02');

INSERT INTO comment (comment_id, book_id, member_id, content, author, reg_time, update_time)
VALUES (4,1,2, '난 별로였음', 'user02', '2025-01-03', '2025-01-03');
INSERT INTO comment (comment_id, book_id, member_id, content, author, reg_time, update_time)
VALUES (5,2,2, '난 별로였음', 'user02', '2025-01-03', '2025-01-03');
INSERT INTO comment (comment_id, book_id, member_id, content, author, reg_time, update_time)
VALUES (6,3,2, '난 별로였음', 'user02', '2025-01-03', '2025-01-03');


--------------------------------------------------------
-- LIKE 초기 데이터 (ManyToOne: member, book FK 연결)
--------------------------------------------------------
INSERT INTO likes (like_id, member_id, book_id, like_yn)
VALUES (1, 1, 1, TRUE);

INSERT INTO likes (like_id, member_id, book_id, like_yn)
VALUES (2, 2, 1, TRUE);

INSERT INTO likes (like_id, member_id, book_id, like_yn)
VALUES (3, 2, 3, FALSE);

INSERT INTO likes (like_id, member_id, book_id, like_yn)
VALUES (4, 3, 2, TRUE);
