package com.nhnacademy.taskapi.tag.repository;


import com.nhnacademy.taskapi.Tag.domain.Tag;
import com.nhnacademy.taskapi.Tag.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;


    @Test
    void findByNameTest(){
        Tag tag = new Tag();
        tag.setName("test");

        // l
        tagRepository.save(tag);


        Tag result = tagRepository.findByName("test");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("test");
    }
}
