package com.example.demo.repository;

import com.example.demo.domain.Reply;
import com.example.demo.domain.ReplyID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, ReplyID> {
}
