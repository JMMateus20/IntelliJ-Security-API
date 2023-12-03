package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.RegistroCategoriaDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseProductoDTO;
import com.cursos.api.springsecuritycourse.entity.Categoria;
import com.cursos.api.springsecuritycourse.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoriaService {

    ResponseEntity<?> insertar(RegistroCategoriaDTO datos);

    ResponseEntity<Page<Categoria>> listar(Pageable pageable);

    ResponseEntity<?> modificar(Long id, RegistroCategoriaDTO datos);


    ResponseEntity<String> cambiarEstado(Long id, Status status);

    ResponseEntity<?> buscar(Long id);

    ResponseEntity<Page<ResponseProductoDTO>> listarProductos(Long id, Pageable pageable);

    ResponseEntity<?> listarProductos2(Long id);
}
