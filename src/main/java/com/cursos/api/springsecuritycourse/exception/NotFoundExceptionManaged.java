package com.cursos.api.springsecuritycourse.exception;

public class NotFoundExceptionManaged extends RuntimeException{

    public NotFoundExceptionManaged(String mensaje){
        super(mensaje);
    }
}
