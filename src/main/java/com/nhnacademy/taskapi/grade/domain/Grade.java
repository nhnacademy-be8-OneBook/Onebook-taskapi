package com.nhnacademy.taskapi.grade.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grade_id")
    private Integer id;

    @NotBlank
    @Column(name="name", unique=true)
    private String name;

    @NotNull
    private int accumulationRate;

    @NotBlank
    private String description;

    public Grade(String name, int accumulationRate, String description) {
        this.name = name;
        this.accumulationRate = accumulationRate;
        this.description = description;
    }

    public void updateGrade(int accumulationRate, String description) {
        this.accumulationRate = accumulationRate;
        this.description = description;
    }

}
