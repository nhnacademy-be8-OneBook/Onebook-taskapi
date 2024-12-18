package com.nhnacademy.taskapi.Tag.service.Impl;

import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.dto.TagUpdateDTO;
import com.nhnacademy.taskapi.Tag.exception.InvalidTagNameException;
import com.nhnacademy.taskapi.Tag.exception.TagNotFoundException;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import com.nhnacademy.taskapi.Tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


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

    @Override
    public Tag updateTag(TagUpdateDTO dto) {
        Tag tag = tagRepository.findById(dto.getTagId()).orElseThrow(()-> new TagNotFoundException("This Tag Not Exist!"));

        if(Objects.isNull(dto.getTagName()) || dto.getTagName().trim().isEmpty()){
            throw new InvalidTagNameException("This tagName is Null OR Empty !");
        }

        tag.setName(dto.getTagName());
        return tagRepository.save(tag);
    }

    @Override
    public void delete(long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(()-> new TagNotFoundException("This Tag Not Exist!"));
        tagRepository.delete(tag);
    }
}
