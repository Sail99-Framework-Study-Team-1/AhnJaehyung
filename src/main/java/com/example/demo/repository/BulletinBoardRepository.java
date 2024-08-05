package com.example.demo.repository;

import com.example.demo.domain.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {
    @Query(value = "SELECT b FROM BulletinBoard b WHERE b.deletedAt IS NULL ORDER BY b.createdAt DESC")
    List<BulletinBoard> findAllNotDeleted();
}
