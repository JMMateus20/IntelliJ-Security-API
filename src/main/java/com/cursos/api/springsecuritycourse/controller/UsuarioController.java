package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.dto.RegistroUsuarioDTO;
import com.cursos.api.springsecuritycourse.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> insertar(@Valid @RequestBody RegistroUsuarioDTO datos, BindingResult resultado){
        if (resultado.hasErrors()){
            List<String> errores=resultado.getFieldErrors().stream().map(err->"El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return usuarioService.insertar(datos);
    }

}
