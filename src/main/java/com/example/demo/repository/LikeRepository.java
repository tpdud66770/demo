package com.example.demo.repository;

import com.example.demo.domain.Book;
import com.example.demo.domain.Likes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    /**
     * 기존 row가 있을 때 likeYn만 토글
     */
    @Modifying
    @Transactional
    @Query("""
        UPDATE Likes l
        SET l.likeYn = CASE WHEN l.likeYn = true THEN false ELSE true END
        WHERE l.member.id = :memberId
          AND l.book.bookId = :bookId
    """)
    void likeToggle(@Param("bookId") Long bookId,
                    @Param("memberId") Long memberId);

    /**
     * 최초 1회만 INSERT (row가 없을 때만 호출해야 함)
     */
    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO likes (member_id, book_id, like_yn) VALUES (:memberId, :bookId, TRUE)",
            nativeQuery = true
    )
    void insertLike(@Param("bookId") Long bookId,
                    @Param("memberId") Long memberId);

    /**
     * 🔥 토글 판단용 (row 존재 여부)
     * → INSERT / UPDATE 분기 기준
     */
    boolean existsByMember_IdAndBook_BookId(Long memberId, Long bookId);

    /**
     * 🔥 조회용 (현재 좋아요 상태)
     * → 메인페이지 liked 여부 판단
     */
    boolean existsByMember_IdAndBook_BookIdAndLikeYnTrue(Long memberId, Long bookId);

    /**
     * 좋아요 목록 조회
     */
    @Query("""
        SELECT l.book
        FROM Likes l
        WHERE l.member.id = :memberId
          AND l.likeYn = true
    """)
    List<Book> findLikedBooksByMemberId(@Param("memberId") Long memberId);

    /**
     * 🔥 안전한 단건 조회 (중복 row가 있어도 절대 안 터짐)
     * → 토글 후 결과 반환용
     */
    Optional<Likes> findTopByMember_IdAndBook_BookIdOrderByIdDesc(Long memberId, Long bookId);
}

