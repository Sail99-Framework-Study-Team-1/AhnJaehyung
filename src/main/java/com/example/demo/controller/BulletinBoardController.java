package com.example.demo.controller;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.dto.BulletinBoardResponseDTO;
import com.example.demo.service.BulletinBoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/bulletinBoard")
public class BulletinBoardController {

    @Autowired private BulletinBoardService bulletinBoardService;
    @Autowired private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<BulletinBoardResponseDTO>> readAllBulletinBoard() {
        List<BulletinBoard> bulletinBoards = bulletinBoardService.getAllBulletinBoard();
        return new ResponseEntity<>(bulletinBoards.stream().map(BulletinBoard::toResponseDTO).toList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{bulletinBoardID}", method = RequestMethod.GET)
    public ResponseEntity<BulletinBoardResponseDTO> readBulletinBoard(
            @PathVariable Long bulletinBoardID
    ) {
        Optional<BulletinBoard> bulletinBoards = bulletinBoardService.getBulletinBoard(bulletinBoardID);
        return new ResponseEntity<>(bulletinBoards.get().toResponseDTO(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<BulletinBoardResponseDTO> createBulletinBoard(
            @Valid @RequestBody BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        BulletinBoard bulletinBoard = BulletinBoard.fromRequestDTO(bulletinBoardRequestDTO);
        String hashedPassword = passwordEncoder.encode(bulletinBoard.getPassword());
        bulletinBoard.setPassword(hashedPassword);
        bulletinBoard.setCreatedAt(new Date());
        bulletinBoard.setDeletedAt(null);
        return new ResponseEntity<>(bulletinBoardService.postBulletinBoard(bulletinBoard).toResponseDTO(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{bulletinBoardID}", method = RequestMethod.PUT)
    public ResponseEntity<BulletinBoardResponseDTO> updateBulletinBoard(
            @PathVariable Long bulletinBoardID,
            @Valid @RequestBody BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        Optional<BulletinBoard> existingBulletinBoard = bulletinBoardService.getBulletinBoard(bulletinBoardID);

        if (existingBulletinBoard.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!passwordEncoder.matches(bulletinBoardRequestDTO.getPassword(), existingBulletinBoard.get().getPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        BulletinBoard bullletinBoard = existingBulletinBoard.get();
        bullletinBoard.setTitle(bulletinBoardRequestDTO.getTitle());
        bullletinBoard.setContent(bulletinBoardRequestDTO.getContent());
        return new ResponseEntity<>(bulletinBoardService.postBulletinBoard(bullletinBoard).toResponseDTO(), HttpStatus.OK);
    }
}
