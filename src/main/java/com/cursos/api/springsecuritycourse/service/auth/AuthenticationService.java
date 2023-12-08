package com.cursos.api.springsecuritycourse.service.auth;

import com.cursos.api.springsecuritycourse.dto.JwtDTO;
import com.cursos.api.springsecuritycourse.dto.LoginDTO;
import com.cursos.api.springsecuritycourse.entity.Usuario;
import com.cursos.api.springsecuritycourse.exception.NotFoundExceptionManaged;
import com.cursos.api.springsecuritycourse.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {


    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsuarioRepository usuarioRep;


    public JwtDTO login(LoginDTO credenciales){
        UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(credenciales.getUsername(), credenciales.getPassword());
        try{
            authManager.authenticate(upat);
        }catch(Exception e){
            throw new NotFoundExceptionManaged("Username o password incorrectas");
        }

        Usuario usuario=usuarioRep.findByUsername(credenciales.getUsername()).get();
        return new JwtDTO(jwtService.generarToken(usuario, generarExtraClaims(usuario)));
    }

    private Map<String, Object> generarExtraClaims(Usuario usuario){
        Map<String, Object> extraClaims=new HashMap<>();
        extraClaims.put("nombre", usuario.getNombre());
        extraClaims.put("rol", usuario.getRol().getNombre());
        return extraClaims;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Usuario findLoggedUser(){
         String username=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         return usuarioRep.findByUsername(username).get();

    }
}
