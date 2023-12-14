package com.cursos.api.springsecuritycourse.persistence.entity.Security;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String base_path;

    @OneToMany(mappedBy = "module")
    private List<Operation> operations=new ArrayList<>();


    public Module(String name, String base_path) {
        this.name = name;
        this.base_path = base_path;
    }

    public Module() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase_path() {
        return base_path;
    }

    public void setBase_path(String base_path) {
        this.base_path = base_path;
    }
}
