package com.cursos.api.springsecuritycourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsuarioDTO {

    private Long id;

    private String nombre;

    private String username;

    private String rol;
}
