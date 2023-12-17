package com.cursos.api.springsecuritycourse.dto.auth;

public class LogoutResponse {


    private String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public LogoutResponse() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
