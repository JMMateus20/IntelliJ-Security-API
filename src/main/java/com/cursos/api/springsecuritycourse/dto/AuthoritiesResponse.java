package com.cursos.api.springsecuritycourse.dto;

public class AuthoritiesResponse {


    private String rolName;

    private String operationName;

    public AuthoritiesResponse(String rolName, String operationName) {
        this.rolName = rolName;
        this.operationName = operationName;
    }

    public AuthoritiesResponse() {
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
