package com.nhnacademy.taskapi.roles.service;

import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.dto.RoleModifyDto;
import com.nhnacademy.taskapi.roles.dto.RoleRegisterDto;
import com.nhnacademy.taskapi.roles.exception.RoleAlreadyExistsException;
import com.nhnacademy.taskapi.roles.exception.RoleIllegalArgumentException;
import com.nhnacademy.taskapi.roles.repository.RoleRepository;
import com.nhnacademy.taskapi.roles.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    @DisplayName("Get All Roles Successfully")
    void getAllRoleTest() {
        List<Role> roleList = new ArrayList<>();
        Mockito.when(roleRepository.findAll()).thenReturn(roleList);

        roleService.getAllRoles();

        Mockito.verify(roleRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Register Role Successfully")
    void registerRoleTest() {
        roleService.registerRole(new RoleRegisterDto("MEMBER", "일반회원"));

        Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any(Role.class));
    }

    @Test
    @DisplayName("Register Role Failed 1 - DuplicateName")
    void failedRegisterRoleDuplicateNameTest() {
        Mockito.when(roleRepository.existsByName(Mockito.anyString())).thenReturn(true);

        assertThrows(RoleAlreadyExistsException.class, () -> {
            roleService.registerRole(new RoleRegisterDto("MEMBER", "일반회원"));
        });
    }

    @Test
    @DisplayName("Register Role Failed 2 - DB error")
    void failedRegisterRoleDBErrorTest() {
        Mockito.when(roleRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> {
            roleService.registerRole(new RoleRegisterDto("MEMBER", "일반회원"));
        });
    }

    @Test
    @DisplayName("Modify Role Successfully")
    void modifyRoleTest() {
        Role role = Role.createRole("등급", "등급 설명");
        Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        Mockito.when(roleRepository.save(Mockito.any(Role.class))).thenReturn(role);

        roleService.modifyRole(1, new RoleModifyDto("수정", "수정 설명"));

        Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any(Role.class));
        Mockito.verify(roleRepository, Mockito.times(1)).findById(1);
    }

    @Test
    @DisplayName("Modify Role Failed 1 - isDuplicateName")
    void failedModifyRoleDuplicateNameTest() {
        Mockito.when(roleRepository.existsByName(Mockito.anyString())).thenReturn(true);

        assertThrows(RoleAlreadyExistsException.class, () -> {
            roleService.modifyRole(1, new RoleModifyDto("등급", "등급 설명"));
        });
    }

    @Test
    @DisplayName("Modify Role Failed 2 - DB error")
    void failedModifyRoleDBErrorTest() {
        Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(Role.createRole("등급", "등급 설명")));
        Mockito.when(roleRepository.save(Mockito.any(Role.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> {
            roleService.modifyRole(1, new RoleModifyDto("등급", "등급 설명"));
        });
    }

    @Test
    @DisplayName("Remove Role Successfully")
    void removeRoleTest() {
        Mockito.when(roleRepository.existsById(1)).thenReturn(true);

        roleService.deleteRole(1);

        Mockito.verify(roleRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Remove Role Failed - Role doesn't exist")
    void failedRemoveRoleFailedTest() {
        Mockito.when(roleRepository.existsById(1)).thenReturn(false);

        assertThrows(RoleIllegalArgumentException.class, () -> roleService.deleteRole(1));
    }


}
