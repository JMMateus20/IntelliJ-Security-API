package com.cursos.api.springsecuritycourse.service.auth;

import com.cursos.api.springsecuritycourse.entity.Usuario;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.Map;

@Service
public class JWTService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generarToken(Usuario usuario, Map<String, Object> extraClaims){
        Date issuedAt=new Date(System.currentTimeMillis());
        Date expiration=new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());
        return Jwts.builder()
                .header().type("JWT")
                .and()

                .subject(usuario.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generarKey(), Jwts.SIG.HS256)
                .compact();



    }


    private SecretKey generarKey(){
        byte[] secretAsBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }


    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generarKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }
}
