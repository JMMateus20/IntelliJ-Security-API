package com.cursos.api.springsecuritycourse.repository;

import com.cursos.api.springsecuritycourse.entity.Categoria;
import com.cursos.api.springsecuritycourse.entity.Producto;
import com.cursos.api.springsecuritycourse.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Long> {


    @Query("SELECT P FROM Producto AS P WHERE P.status=:stat1 AND P.categoria.status=:stat2")
    Page<Producto> encontrarTodos(@Param("stat1") Status stat1, @Param("stat2") Status stat2, Pageable pageable);

    Page<Producto> findByCategoria(Categoria categoria, Pageable pageable);
}
