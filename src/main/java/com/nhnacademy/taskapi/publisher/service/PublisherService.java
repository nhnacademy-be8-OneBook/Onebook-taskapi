package com.nhnacademy.taskapi.publisher.service;

import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.dto.PublisherUpdateDTO;

import java.util.List;

public interface PublisherService {
    //출판사 등록
    Publisher addPublisher(String name);
    Publisher updatePublisher(PublisherUpdateDTO dto);
    void deletePublisher(long publisherId);
    Publisher getPublisher(String publisherName);
    Publisher addPublisherByAladin(String name);
    List<Publisher> getPublisherList(String name);
}
