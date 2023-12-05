package com.cursos.api.springsecuritycourse.config.security.filter;

import com.cursos.api.springsecuritycourse.entity.Usuario;
import com.cursos.api.springsecuritycourse.exception.NotFoundExceptionManaged;
import com.cursos.api.springsecuritycourse.repository.UsuarioRepository;
import com.cursos.api.springsecuritycourse.service.auth.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UsuarioRepository usuarioRep;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. Obtener encabezado http llamado Authorization
        String authorization=request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //2. obtener token desde el encabezado
        String jwt=authorization.split(" ")[1];
        //3. obtener el subject desde el token, este punto valida el formato del token, firma y fecha de expiracion
        String username=jwtService.extractUsername(jwt);
        Usuario usuario=usuarioRep.findByUsername(username).get();
        //4. Seter objeto authentication dentro de SecurityContextHolder
        UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(
            username, null, usuario.getAuthorities()
        );
        upat.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(upat);

        //5. ejecutar el resto de filtros
        filterChain.doFilter(request, response);
    }
}
