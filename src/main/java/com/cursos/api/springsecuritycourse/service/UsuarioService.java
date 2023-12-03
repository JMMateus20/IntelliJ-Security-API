package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.RegistroUsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {


    ResponseEntity<?> insertar(RegistroUsuarioDTO datos);

}
