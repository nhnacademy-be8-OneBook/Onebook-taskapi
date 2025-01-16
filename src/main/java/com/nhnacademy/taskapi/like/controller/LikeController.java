package com.nhnacademy.taskapi.like.controller;


import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikePlusMinusDTO;
import com.nhnacademy.taskapi.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/like")
public class LikeController {
    private final LikeService likeService;


    @PostMapping
    public ResponseEntity<Like> addLike(@RequestBody LikePlusMinusDTO likePlusMinusDTO) {
        Like like = likeService.plusLike(likePlusMinusDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }



    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@RequestBody LikePlusMinusDTO likePlusMinusDTO) {
        likeService.minusLike(likePlusMinusDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
