package com.example.demo.repository;

import com.example.demo.domain.Reply;
import com.example.demo.domain.ReplyID;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, ReplyID> {
    @Query("SELECT r FROM Reply r WHERE r.deletedAt IS NULL AND r.id.bulletinBoardId = :bulletinBoardId ORDER BY r.createdAt DESC")
    List<Reply> findAllByDeletedAtIsNotNullAndBulletinBoardIDEquals(@Param("bulletinBoardId") Long bulletinBoardId);
}
