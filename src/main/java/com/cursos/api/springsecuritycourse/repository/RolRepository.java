package com.cursos.api.springsecuritycourse.repository;

import com.cursos.api.springsecuritycourse.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
}
