package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return bulletinBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public BulletinBoard postBulletinBoard(
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        BulletinBoard bulletinBoard = BulletinBoard.fromRequestDTO(bulletinBoardRequestDTO);

        String hashedPassword = passwordEncoder.encode(bulletinBoard.getPassword());
        bulletinBoard.setPassword(hashedPassword);

        bulletinBoard.setCreatedAt(new Date());
        bulletinBoard.setDeletedAt(null);

        return bulletinBoardRepository.save(bulletinBoard);
    }

    public BulletinBoard putBulletinBoard(
            Long bulletinBoardID,
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) throws Exception {
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinBoardID);

        if(bulletinBoard.isEmpty()) {
            throw new Exception("No bulletinboard exists");
        }

        BulletinBoard newBulletinBoard = bulletinBoard.get();

        if(!passwordEncoder.matches(bulletinBoardRequestDTO.getPassword(), newBulletinBoard.getPassword())) {
            throw new Exception("Password does not match");
        }

        newBulletinBoard.setTitle(bulletinBoardRequestDTO.getTitle());
        newBulletinBoard.setContent(bulletinBoardRequestDTO.getContent());

        return bulletinBoardRepository.save(newBulletinBoard);
    }

    public Optional<BulletinBoard> getBulletinBoard(Long id){
        return bulletinBoardRepository.findById(id);
    }

    public void deleteBulletinBoard(
            Long bulletinBoardID,
            String password
    ) throws Exception {
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinBoardID);

        if(bulletinBoard.isEmpty()) {
            throw new Exception("No bulletinboard exists");
        }

        BulletinBoard existingBulletinBoard = bulletinBoard.get();

        if(!passwordEncoder.matches(password, existingBulletinBoard.getPassword())) {
            throw new Exception("Password does not match");
        }

        bulletinBoardRepository.deleteById(bulletinBoardID);
    }
}
