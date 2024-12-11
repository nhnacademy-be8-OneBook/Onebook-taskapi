package com.nhnacademy.taskapi.publisher.service;

import com.nhnacademy.taskapi.publisher.domain.Publisher;

public interface PublisherService {
    //출판사 등록
    Publisher addPublisher(String name);
}
