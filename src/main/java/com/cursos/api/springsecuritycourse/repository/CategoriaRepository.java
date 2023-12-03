package com.cursos.api.springsecuritycourse.repository;

import com.cursos.api.springsecuritycourse.entity.Categoria;
import com.cursos.api.springsecuritycourse.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


    Optional<Categoria> findByNombre(String nombre);

    Page<Categoria> findByStatus(Status status, Pageable pageable);


}
