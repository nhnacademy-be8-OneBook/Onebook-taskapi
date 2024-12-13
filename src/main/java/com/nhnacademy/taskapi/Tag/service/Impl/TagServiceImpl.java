package com.nhnacademy.taskapi.Tag.service.Impl;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.Tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    // 관리자가 태그 등록
    @Override
    public Tag addTag(String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);

        return tagRepository.save(tag);
    }
}
