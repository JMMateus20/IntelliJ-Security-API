package com.cursos.api.springsecuritycourse.dto;

import com.cursos.api.springsecuritycourse.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class RegistroProductoDTO {


    @NotBlank(message = "no puede estar vacío")
    private String nombre;

    @NotNull(message = "no puede estar vacío")
    private BigDecimal precio;

    @NotNull(message = "no puede estar vacío")
    private Long categoriaID;

    public RegistroProductoDTO(String nombre, BigDecimal precio, Long categoriaID) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoriaID=categoriaID;

    }

    public RegistroProductoDTO() {
    }

    public Long getCategoriaID() {
        return categoriaID;
    }



    public void setCategoriaID(Long categoriaID) {
        this.categoriaID = categoriaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
