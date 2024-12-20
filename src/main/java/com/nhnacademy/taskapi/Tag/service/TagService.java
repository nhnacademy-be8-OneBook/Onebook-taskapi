package com.nhnacademy.taskapi.Tag.service;

import com.nhnacademy.taskapi.Tag.dto.request.CreateTagRequest;
import com.nhnacademy.taskapi.Tag.dto.request.UpdateTagRequest;
import com.nhnacademy.taskapi.Tag.dto.response.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    TagResponse createTag(CreateTagRequest createTagRequest);
    TagResponse getTag(Long tagId);
    List<TagResponse> getAllTags();
    Page<TagResponse> getAllTags(Pageable pageable);
    TagResponse updateTag(UpdateTagRequest updateTagRequest);
    void removeTag(Long tagId);
}
