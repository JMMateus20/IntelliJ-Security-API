package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.SaveOperation;
import com.cursos.api.springsecuritycourse.dto.UpdateOperation;
import org.springframework.http.ResponseEntity;

public interface OperationService {

    ResponseEntity<?> insertar(SaveOperation saveOperation);


    ResponseEntity<?> verRoles(Long id);


    ResponseEntity<?> update(Long id, UpdateOperation datos);
}
