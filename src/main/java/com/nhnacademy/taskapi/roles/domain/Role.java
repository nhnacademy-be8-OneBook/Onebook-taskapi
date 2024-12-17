package com.nhnacademy.taskapi.roles.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "roles")
public class Role {

    /* 1: MEMBER, 2: ADMIN */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    public static Role createRole(String name, String description) {
        return new Role(name, description);
    }

    public void modifyRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
