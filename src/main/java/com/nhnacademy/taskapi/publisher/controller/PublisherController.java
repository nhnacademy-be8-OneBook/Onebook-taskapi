package com.nhnacademy.taskapi.publisher.controller;

import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.dto.PublisherUpdateDTO;
import com.nhnacademy.taskapi.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task/publisher")
public class PublisherController {
    private final PublisherService publisherService;


    @PostMapping("/{puhlisherName}")
    public ResponseEntity<Publisher> createPublisher(@PathVariable String puhlisherName){
        Publisher publisher = publisherService.addPublisher(puhlisherName);
        return ResponseEntity.ok().body(publisher);
    }

    @PutMapping
    public ResponseEntity<Publisher> modifyPublisher(@RequestBody PublisherUpdateDTO dto){
        Publisher publisher = publisherService.updatePublisher(dto);
        return ResponseEntity.ok().body(publisher);
    }


    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deletePublisher(long publisherId){
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.noContent().build();
    }


}
