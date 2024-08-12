package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BulletinBoardService {
    @Autowired BulletinBoardRepository bulletinBoardRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<BulletinBoard> getAllBulletinBoard(){
        return bulletinBoardRepository.findAllNotDeleted();
    }

    public BulletinBoard postBulletinBoard(
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        BulletinBoard bulletinBoard = BulletinBoard.fromRequestDTO(bulletinBoardRequestDTO);

        bulletinBoard.hashPassword(passwordEncoder);
        bulletinBoard.setCreatedAt(new Date());
        bulletinBoard.setDeletedAt(null);

        return bulletinBoardRepository.save(bulletinBoard);
    }

    public BulletinBoard putBulletinBoard(
            Long bulletinBoardID,
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) throws Exception {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findById(bulletinBoardID)
                .orElseThrow(() -> new Exception("No bulletinboard exists"));

        if(!bulletinBoard.matchPassword(bulletinBoardRequestDTO.getPassword(), passwordEncoder)) {
            throw new Exception("Password does not match");
        }

        bulletinBoard.setTitle(bulletinBoardRequestDTO.getTitle());
        bulletinBoard.setContent(bulletinBoardRequestDTO.getContent());
        return bulletinBoard;
    }

    public Optional<BulletinBoard> getBulletinBoard(Long id){
        return bulletinBoardRepository.findById(id);
    }

    public void deleteBulletinBoard(
            Long bulletinBoardID,
            String password
    ) throws Exception {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findById(bulletinBoardID)
                .orElseThrow(() -> new Exception("No bulletinboard exists"));

        if(!bulletinBoard.matchPassword(password, passwordEncoder)) {
            throw new Exception("Password does not match");
        }

        bulletinBoard.setDeletedAt(new Date());
        bulletinBoardRepository.save(bulletinBoard);
    }
}
