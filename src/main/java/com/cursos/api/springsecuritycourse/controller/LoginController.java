package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.dto.LoginDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseUsuarioDTO;
import com.cursos.api.springsecuritycourse.entity.Usuario;
import com.cursos.api.springsecuritycourse.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationService authSevice;


    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO credenciales, BindingResult result){
        if (result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(err->"el campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(authSevice.login(credenciales));

    }

    @GetMapping
    public ResponseEntity<Boolean> validateToken(@RequestParam String jwt){
        boolean isTokenValid=authSevice.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }


    @GetMapping("/profile")
    public ResponseEntity<?> obtenerUsuarioLogeado(){
        Usuario usuario=authSevice.findLoggedUser();
        ResponseUsuarioDTO response=new ResponseUsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getUsername(), usuario.getRol().getNombre());
        return ResponseEntity.ok(response);
    }



}
