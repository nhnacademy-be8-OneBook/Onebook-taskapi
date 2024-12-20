package com.nhnacademy.taskapi.publisher.service;


import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.dto.PublisherUpdateDTO;
import com.nhnacademy.taskapi.publisher.exception.InvalidPublisherNameException;
import com.nhnacademy.taskapi.publisher.exception.PublisherAlreadyExistException;
import com.nhnacademy.taskapi.publisher.exception.PublisherNotFoundException;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.publisher.service.serviceImpl.PublisherServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {
    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private Publisher publisher;



    @Test
    @DisplayName("addPublisher_Success")
    void addPubliser_Success() {
        publisher = new Publisher();
        publisher.setName("test");

        when(publisherRepository.findByName(any(String.class))).thenReturn(null);
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher result = publisherService.addPublisher("test");

        assertNotNull(result);
        assertEquals("test", result.getName());
        verify(publisherRepository).save(any(Publisher.class));
    }


    @Test
    @DisplayName("addPublisher_InvalidPublisherNameException_Empty")
    void addPubliser_Fail_EmptyName() {
        publisher = new Publisher();
        String name = "";
        publisher.setName(name);

        Assertions.assertThrows(InvalidPublisherNameException.class, ()-> publisherService.addPublisher(name));
        verify(publisherRepository, never()).save(any(Publisher.class));
    }

    @Test
    @DisplayName("addPublisher_InvalidPublisherNameException_Null")
    void addPubliser_Fail_NullName() {
        publisher = new Publisher();
        String name = null;
        publisher.setName(name);

        Assertions.assertThrows(InvalidPublisherNameException.class, ()-> publisherService.addPublisher(name));
        verify(publisherRepository, never()).save(any(Publisher.class));
    }

    @Test
    @DisplayName("addPublisher_PublisherAlreadyExistException")
    void addPublisher_Fail_PublisherAlreadyExist() {
        publisher = new Publisher();
        publisher.setName("test");
        publisherService.addPublisher("test");
        when(publisherRepository.findByName(any(String.class))).thenReturn(publisher);

        Assertions.assertThrows(PublisherAlreadyExistException.class, ()-> publisherService.addPublisher("test"));
    }

    @Test
    @DisplayName("updatePublisher_Success")
    void updatePublisher_Success() {
        String UpdateName = "updateTest";

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("existTest");

        PublisherUpdateDTO dto = new PublisherUpdateDTO();

        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(UpdateName);


        when(publisherRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(publisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher result = publisherService.updatePublisher(dto);

        assertNotNull(result);
        assertEquals(UpdateName, result.getName());
        verify(publisherRepository).save(any(Publisher.class));
    }

    @Test
    @DisplayName("updatePublisher_Fail_PublisherNotFoundException")
    void updatePublisher_Fail_NotFoundPublisher() {
        PublisherUpdateDTO dto = new PublisherUpdateDTO();
        dto.setPublisherId(1L);
        dto.setPublisherName("updateTest");
        Assertions.assertThrows(PublisherNotFoundException.class, () -> publisherService.updatePublisher(dto));
    }

    @Test
    @DisplayName("updatePublisher_Fail_InvalidPublisherNameException_Null")
    void updatePublisher_Fail_InvalidPublisherName() {
        String UpdateName = null;

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("existTest");

        PublisherUpdateDTO dto = new PublisherUpdateDTO();

        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(UpdateName);


        when(publisherRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(publisher));

        Assertions.assertThrows(InvalidPublisherNameException.class, () -> publisherService.updatePublisher(dto));
    }

    @Test
    @DisplayName("updatePublisher_Fail_InvalidPublisherNameException_Empty")
    void updatePublisher_Fail_InvalidPublisherName_Empty() {
        String UpdateName = "";

        Publisher publisher = new Publisher();
        publisher.setPublisherId(1L);
        publisher.setName("existTest");

        PublisherUpdateDTO dto = new PublisherUpdateDTO();

        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(UpdateName);


        when(publisherRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(publisher));

        Assertions.assertThrows(InvalidPublisherNameException.class, () -> publisherService.updatePublisher(dto));
    }


    @Test
    @DisplayName("deletePublisher_Success")
    void deletePublisher_Success() {
        long publisherId = 1L;
        publisher = new Publisher();
        publisher.setPublisherId(publisherId);
        publisher.setName("existTest");

        when(publisherRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(publisher));


        publisherService.deletePublisher(publisherId);
        verify(publisherRepository).delete(publisher);
    }
}
