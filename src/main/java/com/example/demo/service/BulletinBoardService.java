package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDTO);

        bulletinBoard.hashPassword(passwordEncoder);
        bulletinBoard.setCreatedAt(new Date());
        bulletinBoard.setDeletedAt(null);

        return bulletinBoardRepository.save(bulletinBoard);
    }

    public Optional<BulletinBoard> getBulletinBoard(Long id){
        return bulletinBoardRepository.findById(id);
    }

    public BulletinBoard putBulletinBoard(
            Long bulletinBoardID,
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) throws ResponseStatusException {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findById(bulletinBoardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bulletinBoard.verifyPasswordOrElseThrow(bulletinBoardRequestDTO.getPassword(), passwordEncoder);

        bulletinBoard.setTitle(bulletinBoardRequestDTO.getTitle());
        bulletinBoard.setContent(bulletinBoardRequestDTO.getContent());

        return bulletinBoard;
    }

    public void deleteBulletinBoard(
            Long bulletinBoardID,
            String password
    ) throws ResponseStatusException {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findById(bulletinBoardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bulletinBoard.verifyPasswordOrElseThrow(password, passwordEncoder);

        bulletinBoard.setDeletedAt(new Date());
    }
}
