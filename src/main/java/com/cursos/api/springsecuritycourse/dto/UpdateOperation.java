package com.cursos.api.springsecuritycourse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateOperation {


    @NotBlank(message = "no puede estar vacío")
    private String name;
    @NotBlank(message = "no puede estar vacío")
    private String path;

    @NotNull(message = "no puede estar vacío")
    private boolean permitAll;

    public UpdateOperation() {
    }

    public UpdateOperation(String name, String path, boolean permitAll) {
        this.name = name;
        this.path = path;
        this.permitAll = permitAll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPermitAll() {
        return permitAll;
    }

    public void setPermitAll(boolean permitAll) {
        this.permitAll = permitAll;
    }
}
