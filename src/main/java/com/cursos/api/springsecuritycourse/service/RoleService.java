package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.SaveRole;
import org.springframework.http.ResponseEntity;

public interface RoleService {

    ResponseEntity<?> insertar(SaveRole saveRole);


    ResponseEntity<?> agregarOperacion(Long idRole, Long idOperacion);

    ResponseEntity<?> eliminarOperacion(Long idRole, Long idOperacion);


    ResponseEntity<?> verOperaciones(Long idRole);
}
