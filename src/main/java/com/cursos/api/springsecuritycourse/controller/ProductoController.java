package com.cursos.api.springsecuritycourse.controller;

import com.cursos.api.springsecuritycourse.dto.RegistroProductoDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseProductoDTO;
import com.cursos.api.springsecuritycourse.entity.Producto;
import com.cursos.api.springsecuritycourse.entity.Status;
import com.cursos.api.springsecuritycourse.service.ProductoService;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ResponseProductoDTO>> findAll(@PathVariable Integer page){
        Page<ResponseProductoDTO> productoPage=productoService.findAll(PageRequest.of(page, 2));
        if (productoPage.hasContent()){
            return ResponseEntity.ok(productoPage);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        return productoService.buscar(id);

    }

    @PostMapping
    public ResponseEntity<?> insertar(@Valid @RequestBody RegistroProductoDTO datos, BindingResult resultado){
        if (resultado.hasErrors()){
            List<String> errores=resultado.getFieldErrors().stream().map(err->{
                return "el campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return productoService.insertar(datos);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id, @RequestParam(name = "status") Status status){
        return productoService.cambiarEstado(id, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody RegistroProductoDTO datos, BindingResult resultado){
        if (resultado.hasErrors()){
            List<String> errores=resultado.getFieldErrors().stream().map(err->{
                return "el campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return productoService.actualizar(id, datos);
    }
}
