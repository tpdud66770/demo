package com.example.demo.repository;

import com.example.demo.domain.Likes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes,Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Likes l " +
            "SET l.like_yn = CASE WHEN l.like_yn = true THEN false ELSE true END " +
            "WHERE l.member.id = :memberId AND l.book.bookId = :bookId")
    void likeToggle(@Param("memberId") Long memberId,
                    @Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO likes (member_id, book_id, like_yn) " +
            "VALUES (:memberId, :bookId, TRUE)",
            nativeQuery = true)
    void insertLike(@Param("memberId") Long memberId,
                    @Param("bookId") Long bookId);

    boolean existsByMember_IdAndBook_BookId(Long memberId, Long bookId);
}
