package com.nhnacademy.taskapi.roles.service;

import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.dto.RoleModifyDto;
import com.nhnacademy.taskapi.roles.dto.RoleRegisterDto;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Integer id);
    boolean isDuplicateName(String name);
    Role registerRole(RoleRegisterDto roleRegisterDto);
    Role modifyRole(Integer id, RoleModifyDto roleModifyDto);
    void deleteRole(Integer id);
}
