package com.nhnacademy.taskapi.roles.service.impl;

import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.dto.RoleModifyDto;
import com.nhnacademy.taskapi.roles.dto.RoleRegisterDto;
import com.nhnacademy.taskapi.roles.exception.RoleAlreadyExistsException;
import com.nhnacademy.taskapi.roles.exception.RoleDataIntegrityViolationException;
import com.nhnacademy.taskapi.roles.exception.RoleIllegalArgumentException;
import com.nhnacademy.taskapi.roles.exception.RoleNotFoundException;
import com.nhnacademy.taskapi.roles.repository.RoleRepository;
import com.nhnacademy.taskapi.roles.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    // 모든 role 조회
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // 단일 role 조회
    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Not Found Role by" + id));
    }

    // Role name 중복 체크
    @Override
    public boolean isDuplicateName(String name) {
        return roleRepository.existsByName(name);
    }

    // role 등록
    @Override
    public Role registerRole(RoleRegisterDto roleRegisterDto) {

        if(isDuplicateName(roleRegisterDto.name())) {
            throw new RoleAlreadyExistsException("Role Name already exists");
        }

        try {

            Role role = Role.createRole(roleRegisterDto.name(), roleRegisterDto.description());
            return roleRepository.save(role);

        }catch(DataIntegrityViolationException e) {
            throw new RoleDataIntegrityViolationException("Failed to save role in the database");
        }
    }

    // role 수정
    @Override
    public Role modifyRole(Integer id, RoleModifyDto roleModifyDto) {

        if(isDuplicateName(roleModifyDto.name())) {
            throw new RoleAlreadyExistsException("Role Name already exists");
        }

        try {

            Role role = getRoleById(id);
            role.modifyRole(roleModifyDto.name(), roleModifyDto.description());
            return roleRepository.save(role);

        }catch(DataIntegrityViolationException e) {
            throw new RoleDataIntegrityViolationException("Failed to save role in the database");
        }

    }

    // role 삭제
    @Override
    public void deleteRole(Integer id) {
        if(!roleRepository.existsById(id)) {
            throw new RoleIllegalArgumentException("Role does not exist with the given ID");
        }

        roleRepository.deleteById(id);
    }
}
