package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.service.AuthroritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorities")
public class AuthoritiesController {

    @Autowired
    private AuthroritiesService authroritiesService;

    @GetMapping("/{idRole}")
    public ResponseEntity<?> listarPorRol(@PathVariable Long idRole){
        return authroritiesService.listarPorRol(idRole);
    }

}
