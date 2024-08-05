package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BulletinBoardService {
    @Autowired BulletinBoardRepository bulletinBoardRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<BulletinBoard> getAllBulletinBoard(){
        return bulletinBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Optional<BulletinBoard> getBulletinBoard(Long id){
        return bulletinBoardRepository.findById(id);
    }

    public BulletinBoard postBulletinBoard(BulletinBoard bulletinBoard) {
        String password = bulletinBoard.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        bulletinBoard.setPassword(hashedPassword);
        return bulletinBoardRepository.save(bulletinBoard);
    }
}
