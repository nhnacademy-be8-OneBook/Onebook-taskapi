package com.nhnacademy.taskapi.publisher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherUpdateDTO {
    private long publisherId;
    private String publisherName;
}
