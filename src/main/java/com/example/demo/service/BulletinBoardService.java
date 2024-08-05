package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulletinBoardService {
    @Autowired BulletinBoardRepository bulletinBoardRepository;

    public List<BulletinBoard> getAllBulletinBoard(){
        return bulletinBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public BulletinBoard postBulletinBoard(BulletinBoard bulletinBoard) {
        return bulletinBoardRepository.save(bulletinBoard);
    }
}
