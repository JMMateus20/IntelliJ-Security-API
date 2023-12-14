package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.dto.SaveRole;
import com.cursos.api.springsecuritycourse.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<?> insertar(@Valid @RequestBody SaveRole saveRole, BindingResult result){
        if (result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(err->"El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return roleService.insertar(saveRole);
    }

    @PostMapping("/operation/permission")
    public ResponseEntity<?> agregarOperacion(@RequestParam(name = "rol") Long idRole,@RequestParam(name="operacion") Long idOperacion){
        return roleService.agregarOperacion(idRole, idOperacion);
    }

    @DeleteMapping("/operation/permission")
    public ResponseEntity<?> eliminarOperacion(@RequestParam(name = "rol") Long idRole,@RequestParam(name="operacion") Long idOperacion){
        return roleService.eliminarOperacion(idRole, idOperacion);
    }

    @GetMapping("/operation/{idRole}")
    public ResponseEntity<?> verOperaciones(@PathVariable Long idRole){
        return roleService.verOperaciones(idRole);
    }
}
