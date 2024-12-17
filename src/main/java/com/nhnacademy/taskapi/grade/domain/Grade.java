package com.nhnacademy.taskapi.grade.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="grade_id")
    private Integer id; // default(REGULAR) ID: 1

    @NotBlank
    @Column(name="name", unique=true)
    private String name;

    @Column(name="accumulate_rate", nullable=false)
    private int accumulationRate;

    @NotBlank
    private String description;

    public static Grade create(String name, int accumulationRate, String description) {
        return new Grade(name, accumulationRate, description);
    }

    public void modifyGrade(String name, int accumulationRate, String description) {
        this.name = name;
        this.accumulationRate = accumulationRate;
        this.description = description;
    }

    private Grade(String name, int accumulationRate, String description) {
        this.name = name;
        this.accumulationRate = accumulationRate;
        this.description = description;
    }

}
