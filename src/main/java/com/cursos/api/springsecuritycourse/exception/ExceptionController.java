package com.cursos.api.springsecuritycourse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundExceptionManaged.class)
    public ResponseEntity<String> returnNotFoundException(NotFoundExceptionManaged e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> returnAccessDeniedExceptionManaged(AccessDeniedException e){
        String mensaje="No posee permisos para realizar esta acción, por favor contacte con el administrador en caso de considerarlo.";
        return new ResponseEntity<>(mensaje, HttpStatus.FORBIDDEN);
    }


}
