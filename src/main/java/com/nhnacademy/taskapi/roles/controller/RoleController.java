package com.nhnacademy.taskapi.roles.controller;

import com.nhnacademy.taskapi.grade.dto.GradeModifyDto;
import com.nhnacademy.taskapi.grade.dto.GradeRegisterDto;
import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.dto.RoleModifyDto;
import com.nhnacademy.taskapi.roles.dto.RoleRegisterDto;
import com.nhnacademy.taskapi.roles.dto.RoleResponseDto;
import com.nhnacademy.taskapi.roles.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task/roles")
public class RoleController {

    private final RoleService roleService;

    // 전체 role 조회
    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getRoles() {
        List<Role> roles = roleService.getAllRoles();

        List<RoleResponseDto> roleResponseDtoList = new ArrayList<>();
        for (Role role : roles) {
           roleResponseDtoList.add(RoleResponseDto.from(role));
        }

        return ResponseEntity.ok().body(roleResponseDtoList);
    }

    // 단일 role 조회
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Integer id) {
        Role role = roleService.getRoleById(id);
        RoleResponseDto roleResponseDto = RoleResponseDto.from(role);

        return ResponseEntity.ok().body(roleResponseDto);
    }

    // role 등록
    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody @Valid RoleRegisterDto roleRegisterDto) {
        Role role = roleService.registerRole(roleRegisterDto);
        RoleResponseDto roleResponseDto = RoleResponseDto.from(role);

        return ResponseEntity.ok().body(roleResponseDto);
    }

    // role 수정
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Integer id, @RequestBody @Valid RoleModifyDto roleModifyDto) {
        Role role = roleService.modifyRole(id, roleModifyDto);
        RoleResponseDto roleResponseDto = RoleResponseDto.from(role);

        return ResponseEntity.ok().body(roleResponseDto);
    }

    // role 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }


}
