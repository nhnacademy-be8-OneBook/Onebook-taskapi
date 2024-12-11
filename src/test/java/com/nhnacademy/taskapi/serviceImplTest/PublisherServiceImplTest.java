package com.nhnacademy.taskapi.serviceImplTest;

import com.nhnacademy.taskapi.publisher.domain.Publisher;
import com.nhnacademy.taskapi.publisher.repository.PublisherRepository;
import com.nhnacademy.taskapi.publisher.service.serviceImpl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PublisherServiceImplTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPublisher() {
        // Given
        String publisherName = "Test Publisher";
        Publisher mockPublisher = new Publisher();
        mockPublisher.setName(publisherName);

        when(publisherRepository.save(any(Publisher.class))).thenReturn(mockPublisher);

        // When
        Publisher result = publisherService.addPublisher(publisherName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(publisherName);

        verify(publisherRepository, times(1)).save(any(Publisher.class));
    }

}
