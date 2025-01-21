package com.nhnacademy.taskapi.like.controller;


import com.nhnacademy.taskapi.like.domain.Like;
import com.nhnacademy.taskapi.like.dto.LikePlusMinusDTO;
import com.nhnacademy.taskapi.like.dto.LikeReponse;
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


    @PostMapping("/{bookId}")
    public ResponseEntity<LikeReponse> addLike(@PathVariable long bookId,
                                               @RequestHeader("X-MEMBER-ID") long memberId) {

        LikeReponse like = likeService.plusLike(bookId, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }



    @DeleteMapping
    public ResponseEntity<Void> deleteLike(@RequestBody LikePlusMinusDTO likePlusMinusDTO) {
        likeService.minusLike(likePlusMinusDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<LikeReponse> getLike(@PathVariable("bookId") long bookId) {
        LikeReponse like = likeService.getLikeByBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(like);
    }


}
