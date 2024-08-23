package com.example.demo.controller;

import com.example.demo.domain.BulletinBoard;
import com.example.demo.dto.BulletinBoardRequestDTO;
import com.example.demo.dto.BulletinBoardResponseDTO;
import com.example.demo.service.BulletinBoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/bulletinBoard")
public class BulletinBoardController {

    @Autowired private BulletinBoardService bulletinBoardService;

    @GetMapping(value = "")
    public ResponseEntity<List<BulletinBoardResponseDTO>> readAllBulletinBoard() {
        List<BulletinBoard> bulletinBoards = bulletinBoardService.getAllBulletinBoard();
        return new ResponseEntity<>(bulletinBoards.stream().map(BulletinBoard::toResponseDTO).toList(), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<BulletinBoardResponseDTO> createBulletinBoard(
            @Valid @RequestBody BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) {
        return new ResponseEntity<>(
                bulletinBoardService
                        .postBulletinBoard(bulletinBoardRequestDTO)
                        .toResponseDTO(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{bulletinBoardID}")
    public ResponseEntity<BulletinBoardResponseDTO> readBulletinBoard(
            @PathVariable Long bulletinBoardID
    ) {
        Optional<BulletinBoard> bulletinBoards = bulletinBoardService.getBulletinBoard(bulletinBoardID);
        return new ResponseEntity<>(bulletinBoards.get().toResponseDTO(), HttpStatus.OK);
    }

    @PutMapping(value = "/{bulletinBoardID}")
    public ResponseEntity<BulletinBoardResponseDTO> updateBulletinBoard(
            @PathVariable Long bulletinBoardID,
            @Valid @RequestBody BulletinBoardRequestDTO bulletinBoardRequestDTO
    ) throws Exception {
        return new ResponseEntity<>(
                bulletinBoardService
                        .putBulletinBoard(bulletinBoardID, bulletinBoardRequestDTO)
                        .toResponseDTO(),
                HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{bulletinBoardID}")
    public ResponseEntity<Object> deleteBulletinBoard(
            @PathVariable Long bulletinBoardID
    ) throws Exception {
        bulletinBoardService
                .deleteBulletinBoard(bulletinBoardID);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
