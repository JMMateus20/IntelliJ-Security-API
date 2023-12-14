package com.cursos.api.springsecuritycourse.service.impl;

import com.cursos.api.springsecuritycourse.dto.AuthoritiesResponse;
import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Role;
import com.cursos.api.springsecuritycourse.persistence.repository.AuthoritiesRepository;
import com.cursos.api.springsecuritycourse.persistence.repository.RoleRepository;
import com.cursos.api.springsecuritycourse.service.AuthroritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthoritiesServiceImpl implements AuthroritiesService {

    @Autowired
    private RoleRepository roleRep;

    @Autowired
    private AuthoritiesRepository authoritiesRep;


    @Override
    public ResponseEntity<?> listarPorRol(Long idRole) {
        Map<String, Object> respuesta=new HashMap<>();
        Role role=roleRep.findById(idRole).orElseThrow(()->new ObjectNotFoundException("Rol no encontrado"));
        List<AuthoritiesResponse> ListaPermisos=authoritiesRep.encontrarPorRol(role);
        respuesta.put("permisos", ListaPermisos);
        return ResponseEntity.ok(respuesta);

    }
}
