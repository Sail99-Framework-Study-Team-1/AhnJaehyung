package com.example.demo.repository;

import com.example.demo.domain.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {
}
