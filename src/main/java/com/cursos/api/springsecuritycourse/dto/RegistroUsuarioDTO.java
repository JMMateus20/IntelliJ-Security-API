package com.cursos.api.springsecuritycourse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {

    @NotBlank(message = "no puede estar vacío")
    @Size(min = 4, message = "debe tener al menos cuatro caracteres")
    private String nombre;
    @NotBlank(message = "no puede estar vacío")
    private String username;
    @NotBlank(message = "no puede estar vacío")
    private String password;
    @NotBlank(message = "no puede estar vacío")
    private String repeatedPassword;



}
