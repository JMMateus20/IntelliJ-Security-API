package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.dto.RegistroCategoriaDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseProductoDTO;
import com.cursos.api.springsecuritycourse.entity.Categoria;
import com.cursos.api.springsecuritycourse.entity.Status;
import com.cursos.api.springsecuritycourse.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<?> insertar(@Valid @RequestBody RegistroCategoriaDTO datos, BindingResult resultado){
        if (resultado.hasErrors()){
            List<String> errores=resultado.getFieldErrors().stream().map(err->{
                return "el campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return categoriaService.insertar(datos);
    }

    @PreAuthorize("hasAnyRole('admin', 'assistant')")
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Categoria>> listar(@PathVariable Integer page){
        return categoriaService.listar(PageRequest.of(page, 2));
    }

    @PreAuthorize("hasAnyRole('admin', 'assistant')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @Valid @RequestBody RegistroCategoriaDTO datos, BindingResult resultado){
        if (resultado.hasErrors()){
            List<String> errores=resultado.getFieldErrors().stream().map(err->{
                return "el campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return categoriaService.modificar(id, datos);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/status/{id}")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id, @RequestParam(name = "status") Status status){
        return categoriaService.cambiarEstado(id, status);
    }

    @PreAuthorize("hasAnyRole('admin', 'assistant')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        return categoriaService.buscar(id);
    }

    @PreAuthorize("hasAnyRole('admin', 'assistant')")
    @GetMapping("/productos/{id}")
    public ResponseEntity<Page<ResponseProductoDTO>> listarProductos(@PathVariable Long id, @RequestParam(name="page") Integer page){
        return categoriaService.listarProductos(id, PageRequest.of(page, 2));
    }

    @GetMapping("/productos2/{id}")
    public ResponseEntity<?> listarProductos2(@PathVariable Long id){
        return categoriaService.listarProductos2(id);
    }


}


