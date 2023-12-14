package com.cursos.api.springsecuritycourse.service.impl;

import com.cursos.api.springsecuritycourse.dto.OperationResponse;
import com.cursos.api.springsecuritycourse.dto.SaveOperation;
import com.cursos.api.springsecuritycourse.dto.UpdateOperation;
import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Module;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Operation;
import com.cursos.api.springsecuritycourse.persistence.repository.ModuleRepository;
import com.cursos.api.springsecuritycourse.persistence.repository.OperationRepository;
import com.cursos.api.springsecuritycourse.service.OperationService;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRep;

    @Autowired
    private ModuleRepository moduleRep;
    @Override
    public ResponseEntity<?> insertar(SaveOperation saveOperation) {
        Map<String, Object> respuesta=new HashMap<>();
        if (operationRep.findByName(saveOperation.getName()).isPresent()){
            respuesta.put("mensaje", "Ya existe una operación con este nombre");
            return ResponseEntity.badRequest().body(respuesta);
        }
        Module module=moduleRep.findById(saveOperation.getModuleId()).orElseThrow(()->new ObjectNotFoundException("Modulo no encontrado"));
        Operation operationNew=new Operation(saveOperation.getName(), saveOperation.getPath(), saveOperation.getHttpMethod(), saveOperation.isPermitAll(), module);
        operationRep.save(operationNew);
        OperationResponse operationResponse=new OperationResponse(operationNew.getId(), operationNew.getName(), operationNew.isPermitAll());
        respuesta.put("operacion", operationResponse);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> verRoles(Long id) {
        Map<String, Object> respuesta=new HashMap<>();
        Operation operationBD=operationRep.findById(id).orElseThrow(()->new ObjectNotFoundException("Operación no encontrada"));
        respuesta.put("roles", operationBD.getRoles());
        return ResponseEntity.ok(respuesta);

    }

    @Override
    public ResponseEntity<?> update(Long id, UpdateOperation datos) {
        Operation operationBD=operationRep.findById(id).orElseThrow(()->new ObjectNotFoundException("Operación no encontrada"));
        operationBD.setName(datos.getName());
        operationBD.setPath(datos.getPath());
        operationBD.setPermitAll(datos.isPermitAll());
        operationRep.save(operationBD);
        OperationResponse response=new OperationResponse(operationBD.getId(), operationBD.getName(), operationBD.isPermitAll());
        return ResponseEntity.ok(response);
    }
}
