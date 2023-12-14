package com.cursos.api.springsecuritycourse.persistence.entity.Security;

import jakarta.persistence.*;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String name;

    private String path;

    private String httpMethod;

    private boolean permitAll;


    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="module_id")
    private Module module;

    @ManyToMany(mappedBy = "operations")
    private List<Role> roles=new ArrayList<>();


    public Operation(String name, String path, String httpMethod, boolean permitAll, Module module) {
        this.name = name;
        this.path = path;
        this.httpMethod = httpMethod;
        this.permitAll = permitAll;
        this.module = module;
    }


    public Operation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isPermitAll() {
        return permitAll;
    }

    public void setPermitAll(boolean permitAll) {
        this.permitAll = permitAll;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
