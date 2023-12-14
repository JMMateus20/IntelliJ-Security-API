package com.cursos.api.springsecuritycourse.persistence.repository;

import com.cursos.api.springsecuritycourse.persistence.entity.Security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    Optional<Operation> findByName(String name);

    @Query("SELECT o FROM Operation AS o WHERE o.permitAll=true")
    List<Operation> findByPublicAccess();
}
