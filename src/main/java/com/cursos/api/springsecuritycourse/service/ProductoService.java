package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.RegistroProductoDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseProductoDTO;
import com.cursos.api.springsecuritycourse.entity.Producto;
import com.cursos.api.springsecuritycourse.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductoService {

    Page<ResponseProductoDTO> findAll(Pageable pageable);


    ResponseEntity<?> buscar(Long id);


    ResponseEntity<?> insertar(RegistroProductoDTO datos);


    ResponseEntity<String> cambiarEstado(Long id, Status status);


    ResponseEntity<?> actualizar(Long id, RegistroProductoDTO datos);
}
