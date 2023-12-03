package com.cursos.api.springsecuritycourse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "no puede estar vacío")
    private String username;
    @NotBlank(message = "no puede estar vacío")
    private String password;
}
