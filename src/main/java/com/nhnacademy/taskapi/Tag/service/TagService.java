package com.nhnacademy.taskapi.Tag.service;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.dto.TagUpdateDTO;

public interface TagService {
    Tag addTag(String tagName);
    Tag updateTag(TagUpdateDTO dto);
    void delete(long tagId);
}
