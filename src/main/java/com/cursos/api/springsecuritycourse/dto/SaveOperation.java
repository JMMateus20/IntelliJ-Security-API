package com.cursos.api.springsecuritycourse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SaveOperation {


    @NotBlank(message = "no puede estar vacío")
    private String name;

    @NotBlank(message = "no puede estar vacío")
    private String path;

    @NotBlank(message = "no puede estar vacío")
    private String httpMethod;

    @NotNull(message = "no puede estar vacío")
    private boolean permitAll;

    @NotNull(message = "no puede estar vacío")
    private Long moduleId;

    public SaveOperation(String name, String path, String httpMethod, boolean permitAll, Long moduleId) {
        this.name = name;
        this.path = path;
        this.httpMethod = httpMethod;
        this.permitAll = permitAll;
        this.moduleId = moduleId;
    }

    public SaveOperation() {
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

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isPermitAll() {
        return permitAll;
    }

    public void setPermitAll(boolean permitAll) {
        this.permitAll = permitAll;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
