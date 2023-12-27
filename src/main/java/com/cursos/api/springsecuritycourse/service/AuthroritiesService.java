package com.cursos.api.springsecuritycourse.service;

import org.springframework.http.ResponseEntity;

public interface AuthroritiesService {



    ResponseEntity<?> listarPorRol(Long idRole);

}
