package com.cursos.api.springsecuritycourse.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Producto(String nombre, BigDecimal precio, Status status, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.status = status;
        this.categoria=categoria;
    }

    public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public Long getId() {
        return id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
