package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.domain.User;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.repository.BulletinBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BulletinBoardService {
    @Autowired private UserService userService;
    @Autowired private BulletinBoardRepository bulletinBoardRepository;

    public List<BulletinBoard> getAllBulletinBoard(){
        return bulletinBoardRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    public BulletinBoard postBulletinBoard(
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        Long userId = userService.getAuthenticatedUserIdElseThrow();
        User user = new User();
        user.setId(userId);

        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDTO);
        bulletinBoard.setAuthor(user);
        bulletinBoard.setCreatedAt(new Date());
        bulletinBoard.setDeletedAt(null);

        return bulletinBoardRepository.save(bulletinBoard);
    }

    public Optional<BulletinBoard> getBulletinBoard(
            Long id
    ){
        return bulletinBoardRepository.findById(id);
    }

    public BulletinBoard putBulletinBoard(
            Long bulletinBoardID,
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) throws ResponseStatusException {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findByIdAndDeletedAtNull(bulletinBoardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userService.throwInvalidUserAccess(bulletinBoard);

        bulletinBoard.setTitle(bulletinBoardRequestDTO.getTitle());
        bulletinBoard.setContent(bulletinBoardRequestDTO.getContent());

        return bulletinBoard;
    }

    public void deleteBulletinBoard(
            Long bulletinBoardID
    ) throws ResponseStatusException {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findByIdAndDeletedAtNull(bulletinBoardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userService.throwInvalidUserAccess(bulletinBoard);

        bulletinBoard.setDeletedAt(new Date());
    }
}
