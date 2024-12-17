package com.nhnacademy.taskapi.roles.repository;

import com.nhnacademy.taskapi.roles.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
}
