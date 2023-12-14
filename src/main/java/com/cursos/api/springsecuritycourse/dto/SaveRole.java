package com.cursos.api.springsecuritycourse.dto;

import jakarta.validation.constraints.NotBlank;

public class SaveRole {

    @NotBlank(message = "no puede estar vacío")
    private String name;


    public SaveRole(){}
    public SaveRole(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
