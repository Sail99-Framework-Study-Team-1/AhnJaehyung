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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/bulletinBoard")
public class BulletinBoardController {

    @Autowired private BulletinBoardService bulletinBoardService;

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
        bulletinBoard.setCreatedAt(new Date());
        bulletinBoard.setDeletedAt(null);
        return new ResponseEntity<>(bulletinBoardService.postBulletinBoard(bulletinBoard).toResponseDTO(), HttpStatus.OK);
    }
}
