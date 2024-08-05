package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BulletinBoardService {
    @Autowired BulletinBoardRepository bulletinBoardRepository;

    public List<BulletinBoard> getAllBulletinBoard(){
        return bulletinBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Optional<BulletinBoard> getBulletinBoard(Long id){
        return bulletinBoardRepository.findById(id);
    }

    public BulletinBoard postBulletinBoard(BulletinBoard bulletinBoard) {
        return bulletinBoardRepository.save(bulletinBoard);
    }
}
