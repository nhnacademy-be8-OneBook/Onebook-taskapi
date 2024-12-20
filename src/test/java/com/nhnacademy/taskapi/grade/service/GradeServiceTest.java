package com.nhnacademy.taskapi.grade.service;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.dto.GradeModifyDto;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;
import com.nhnacademy.taskapi.grade.exception.GradeAlreadyExistsException;
import com.nhnacademy.taskapi.grade.exception.GradeDataIntegrityViolationException;
import com.nhnacademy.taskapi.grade.exception.GradeIllegalArgumentException;
import com.nhnacademy.taskapi.grade.repository.GradeRepository;
import com.nhnacademy.taskapi.grade.service.impl.GradeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    @Test
    @DisplayName("Get All Grade Successfully")
    void getAllGradeTest() {
        List<Grade> gradeList = Arrays.asList(Grade.create("ROYAL", 10, "일반 등급"));
        Mockito.when(gradeRepository.findAll()).thenReturn(gradeList);

        gradeService.getAllGrades();

        Mockito.verify(gradeRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Get Default Grade Successfully")
    void getDefaultGradeTest() {
        Grade grade = Grade.create("test", 1, "test");
        Mockito.when(gradeRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(gradeRepository.findById(1)).thenReturn(Optional.of(grade));

        gradeService.getDefaultGrade();

        Mockito.verify(gradeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("Get One Grade Successfully")
    void getOneGradeTest() {
        Grade grade = Grade.create("test", 1, "테스트");
        Mockito.when(gradeRepository.existsById(1)).thenReturn(true);
        Mockito.when(gradeRepository.findById(1)).thenReturn(Optional.of(grade));

        gradeService.getGradeById(1);

        Mockito.verify(gradeRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("Get One Grade Failed - doesn't exists id")
    void failedGetOneGradeFailed() {
        Mockito.when(gradeRepository.existsById(1)).thenReturn(false);

        assertThrows(GradeIllegalArgumentException.class, ()->gradeService.getGradeById(1));
    }

    @Test
    @DisplayName("Exists Grade by name")
    void existsByNameTest() {
        Mockito.when(gradeRepository.existsByName(Mockito.anyString())).thenReturn(true);

        if(gradeService.existsByName("test")) {
            log.info("grade exists");
        }else {
            log.info("grade doesn't exist");
        }
    }

    @Test
    @DisplayName("Register Grade Successfully")
    void registerGradeTest() {
        Mockito.when(gradeRepository.existsByName(Mockito.anyString())).thenReturn(false);

        gradeService.registerGrade(new GradeRegisterDto("test", 10, "test"));

        Mockito.verify(gradeRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Register Grade Failed 1 - Name Not Found")
    void registerGradeFailedTest1() {
        Mockito.when(gradeRepository.existsByName(Mockito.any())).thenReturn(true);

        assertThrows(GradeAlreadyExistsException.class,
                () -> gradeService.registerGrade(new GradeRegisterDto("test", 1, "test"))
        );
    }

    @Test
    @DisplayName("Register Grade Failed 2 - DB error")
    void registerGradeFailedTest2() {
        Mockito.when(gradeRepository.existsByName(Mockito.anyString())).thenReturn(false);
        Mockito.when(gradeRepository.save(Mockito.any()))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(GradeDataIntegrityViolationException.class,
                ()->gradeService.registerGrade(new GradeRegisterDto("test", 1, "test"))
        );
    }

    @Test
    @DisplayName("Modify Grade Successfully")
    void modifyGradeTest() {
        Grade grade = Grade.create("test", 1, "test");
        Mockito.when(gradeRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(gradeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(grade));

        gradeService.modifyGrade(1, new GradeModifyDto("hello", 1, "world"));

        Mockito.verify(gradeRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    @DisplayName("Modify Grade Failed - Db error")
    void modifyGradeFailedTest() {
        Grade grade = Grade.create("test", 1, "test");
        Mockito.when(gradeRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(gradeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(grade));
        Mockito.when(gradeRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(GradeDataIntegrityViolationException.class,
                ()->gradeService.modifyGrade(1, new GradeModifyDto("test", 1, "test"))
                );

    }

    @Test
    @DisplayName("Remove Grade Successfully")
    void RemoveGrade() {
        Grade grade = Grade.create("test", 1, "test");
        Mockito.when((gradeRepository.existsById(Mockito.anyInt()))).thenReturn(true);
        Mockito.when(gradeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(grade));

        gradeService.removeGrade(Mockito.anyInt());

        Mockito.verify(gradeRepository, Mockito.times(1)).delete(Mockito.any());
    }


}
