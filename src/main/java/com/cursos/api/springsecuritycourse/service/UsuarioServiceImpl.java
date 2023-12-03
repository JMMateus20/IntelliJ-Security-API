package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.RegistroUsuarioDTO;
import com.cursos.api.springsecuritycourse.dto.ResponseUsuarioDTO;
import com.cursos.api.springsecuritycourse.entity.Rol;
import com.cursos.api.springsecuritycourse.entity.Usuario;
import com.cursos.api.springsecuritycourse.exception.NotFoundExceptionManaged;
import com.cursos.api.springsecuritycourse.repository.RolRepository;
import com.cursos.api.springsecuritycourse.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRep;

    @Autowired
    private RolRepository rolRep;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<?> insertar(RegistroUsuarioDTO datos) {
        Map<String, Object> respuesta=new HashMap<>();
        if (usuarioRep.findByUsername(datos.getUsername()).isPresent()){
            respuesta.put("error", "Ya existe un usuario con este username");
            return ResponseEntity.badRequest().body(respuesta);
        }
        if (!datos.getPassword().equals(datos.getRepeatedPassword())){
            respuesta.put("error", "Las contraseñas no coinciden");
            return ResponseEntity.badRequest().body(respuesta);
        }

        Rol rolBD=rolRep.findById(3L).get();
        String passwordEncoded=passwordEncoder.encode(datos.getPassword());
        Usuario usuarioNew=new Usuario(datos.getNombre(), datos.getUsername(), passwordEncoded, rolBD);
        usuarioRep.save(usuarioNew);
        ResponseUsuarioDTO usuarioDTO=new ResponseUsuarioDTO(usuarioNew.getId(), usuarioNew.getNombre(), usuarioNew.getUsername(), usuarioNew.getRol().getNombre());
        respuesta.put("usuario", usuarioDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);


    }
}
