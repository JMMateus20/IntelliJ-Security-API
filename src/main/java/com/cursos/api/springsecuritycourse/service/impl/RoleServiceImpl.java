package com.cursos.api.springsecuritycourse.service.impl;

import com.cursos.api.springsecuritycourse.dto.OperationResponse;
import com.cursos.api.springsecuritycourse.dto.SaveRole;
import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Operation;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Role;
import com.cursos.api.springsecuritycourse.persistence.repository.OperationRepository;
import com.cursos.api.springsecuritycourse.persistence.repository.RoleRepository;
import com.cursos.api.springsecuritycourse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRep;

    @Autowired
    private OperationRepository operationRep;
    @Override
    public ResponseEntity<?> insertar(SaveRole saveRole) {
        Map<String, Object> respuesta=new HashMap<>();
        if (roleRep.findByName(saveRole.getName()).isPresent()) {
            respuesta.put("mensaje", "Ya existe este rol en la base de datos");
            return ResponseEntity.badRequest().body(respuesta);
        }
        Role roleNew=new Role(saveRole.getName());
        return new ResponseEntity<>(roleRep.save(roleNew), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> agregarOperacion(Long idRole, Long idOperacion) {
        Map<String, Object> respuesta=new HashMap<>();
        Operation operationBD=operationRep.findById(idOperacion).orElseThrow(()->new ObjectNotFoundException("Operación no encontrada"));
        Role roleBD=roleRep.findById(idRole).orElseThrow(()->new ObjectNotFoundException("Rol no encontrado"));
        if (roleBD.getOperations().contains(operationBD)){
            respuesta.put("mensaje", "Este rol ya contiene la operación: " + operationBD.getName());
            return ResponseEntity.badRequest().body(respuesta);
        }
        roleBD.getOperations().add(operationBD);
        roleRep.save(roleBD);
        List<OperationResponse> operaciones=roleBD.getOperations().stream().map(op->new OperationResponse(op.getId(), op.getName(), op.isPermitAll())).collect(Collectors.toList());
        respuesta.put("operaciones", operaciones);
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<?> eliminarOperacion(Long idRole, Long idOperacion) {
        Map<String, Object> respuesta=new HashMap<>();
        Operation operationBD=operationRep.findById(idOperacion).orElseThrow(()->new ObjectNotFoundException("Operación no encontrada"));
        Role roleBD=roleRep.findById(idRole).orElseThrow(()->new ObjectNotFoundException("Rol no encontrado"));
        if (!(roleBD.getOperations().contains(operationBD))){
            respuesta.put("mensaje", "Este rol no tiene la operacion: " + operationBD.getName());
            return ResponseEntity.badRequest().body(respuesta);
        }
        roleBD.getOperations().remove(operationBD);
        roleRep.save(roleBD);
        respuesta.put("exito", "Se ha eliminado la operación correctamente");
        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<?> verOperaciones(Long idRole) {
        Role roleBD=roleRep.findById(idRole).orElseThrow(()->new ObjectNotFoundException("Rol no encontrado"));
        List<OperationResponse> operaciones=roleBD.getOperations().stream().map(op->new OperationResponse(op.getId(), op.getName(), op.isPermitAll())).collect(Collectors.toList());
        return ResponseEntity.ok(operaciones);
    }
}
