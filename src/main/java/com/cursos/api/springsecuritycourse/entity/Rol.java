package com.cursos.api.springsecuritycourse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios=new ArrayList<>();


    public Rol(String nombre) {
        this.nombre = nombre;
    }
}
