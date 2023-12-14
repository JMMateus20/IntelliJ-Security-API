package com.cursos.api.springsecuritycourse.dto;


public class OperationResponse {

    private Long id;

    private String name;

    private boolean permitAll;

    public OperationResponse() {
    }

    public OperationResponse(Long id, String name, boolean permitAll) {
        this.id = id;
        this.name = name;
        this.permitAll = permitAll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPermitAll() {
        return permitAll;
    }

    public void setPermitAll(boolean permitAll) {
        this.permitAll = permitAll;
    }
}
