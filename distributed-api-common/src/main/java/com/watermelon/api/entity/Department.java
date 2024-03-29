package com.watermelon.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private int id;
    private String name;
    private int courseNumber;
    private int classNumber;
    private int teacherNumber;
    private int studentNumber;

}
