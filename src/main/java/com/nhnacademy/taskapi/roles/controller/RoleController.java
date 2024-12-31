package com.nhnacademy.taskapi.roles.controller;

import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.dto.RoleModifyRequestDto;
import com.nhnacademy.taskapi.roles.dto.RoleRegisterRequestDto;
import com.nhnacademy.taskapi.roles.dto.RoleResponseDto;
import com.nhnacademy.taskapi.roles.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        List<RoleResponseDto> roleResponseDtoList = roleService.getAllRoles();
        return ResponseEntity.ok().body(roleResponseDtoList);
    }

    // 단일 role 조회
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Integer id) {
        RoleResponseDto roleResponseDto = roleService.getRoleById(id);
        return ResponseEntity.ok().body(roleResponseDto);
    }

    // role 등록
    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody @Valid RoleRegisterRequestDto roleRegisterRequestDto) {
        RoleResponseDto roleResponseDto = roleService.registerRole(roleRegisterRequestDto);
        return ResponseEntity.ok().body(roleResponseDto);
    }

    // role 수정
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Integer id, @RequestBody @Valid RoleModifyRequestDto roleModifyRequestDto) {
        RoleResponseDto roleResponseDto = roleService.modifyRole(id, roleModifyRequestDto);
        return ResponseEntity.ok().body(roleResponseDto);
    }

    // role 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }


}
