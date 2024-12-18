package com.nhnacademy.taskapi.Tag.controller;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.dto.TagUpdateDTO;
import com.nhnacademy.taskapi.Tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;


    @PostMapping("/{tagName}")
    public ResponseEntity<Tag> createTag(@PathVariable String tagName){
        Tag tag = tagService.addTag(tagName);
        return ResponseEntity.ok().body(tag);
    }

    @PutMapping
    public ResponseEntity<Tag> modifyTag(@RequestBody TagUpdateDTO dto){
        Tag tag = tagService.updateTag(dto);
        return ResponseEntity.ok().body(tag);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTag(@PathVariable long tagId){
        tagService.delete(tagId);
        return ResponseEntity.noContent().build();
    }
}
