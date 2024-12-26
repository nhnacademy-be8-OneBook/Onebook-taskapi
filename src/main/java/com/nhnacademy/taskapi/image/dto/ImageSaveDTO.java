package com.nhnacademy.taskapi.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageSaveDTO {
    private byte[] imageBytes;
    private long bookId;
    private String imageName;
}
