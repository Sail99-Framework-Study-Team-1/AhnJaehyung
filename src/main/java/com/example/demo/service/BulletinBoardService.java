package com.example.demo.service;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.domain.User;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.repository.BulletinBoardRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BulletinBoardService {
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private BulletinBoardRepository bulletinBoardRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<BulletinBoard> getAllBulletinBoard(){
        return bulletinBoardRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    public BulletinBoard postBulletinBoard(
            BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        User user = userService.getAuthenticatedUserElseThrow();

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
                .findById(bulletinBoardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        validateNotDeletedBulletinBoardElseThrow(bulletinBoard);
        validateAuthorOrElseThrow(bulletinBoard);

        bulletinBoard.setTitle(bulletinBoardRequestDTO.getTitle());
        bulletinBoard.setContent(bulletinBoardRequestDTO.getContent());

        return bulletinBoard;
    }

    public void deleteBulletinBoard(
            Long bulletinBoardID
    ) throws ResponseStatusException {
        BulletinBoard bulletinBoard = bulletinBoardRepository
                .findById(bulletinBoardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        validateNotDeletedBulletinBoardElseThrow(bulletinBoard);
        validateAuthorOrElseThrow(bulletinBoard);

        bulletinBoard.setDeletedAt(new Date());
    }

    private void validateNotDeletedBulletinBoardElseThrow(
            BulletinBoard bulletinBoard
    ) throws ResponseStatusException {
        if (bulletinBoard.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void validateAuthorOrElseThrow(
            BulletinBoard bulletinBoard
    ) throws ResponseStatusException {
        User userOnSession = userService.getAuthenticatedUserElseThrow();

        if (!Objects.equals(userOnSession.getId(), bulletinBoard.getAuthor().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
