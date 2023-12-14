package com.cursos.api.springsecuritycourse.persistence.repository;

import com.cursos.api.springsecuritycourse.dto.AuthoritiesResponse;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.GrantedPermission;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<GrantedPermission, Long> {

    @Query("SELECT new com.cursos.api.springsecuritycourse.dto.AuthoritiesResponse(G.role.name, G.operation.name) FROM GrantedPermission AS G WHERE G.role=:role")
    List<AuthoritiesResponse> encontrarPorRol(@Param("role") Role role);


}
