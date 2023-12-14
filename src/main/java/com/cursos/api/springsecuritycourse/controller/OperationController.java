package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.dto.SaveOperation;
import com.cursos.api.springsecuritycourse.dto.UpdateOperation;
import com.cursos.api.springsecuritycourse.service.OperationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/operaciones")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<?> insertar(@Valid @RequestBody SaveOperation saveOperation, BindingResult result){
        if (result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(err->"El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return operationService.insertar(saveOperation);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<?> verRoles(@PathVariable Long id){
        return operationService.verRoles(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody UpdateOperation datos, BindingResult result){
        if (result.hasErrors()){
            List<String> errores=result.getFieldErrors().stream().map(err->"El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return operationService.update(id, datos);
    }
}
