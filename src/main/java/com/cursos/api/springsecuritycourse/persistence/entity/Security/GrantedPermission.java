package com.cursos.api.springsecuritycourse.persistence.entity.Security;

import jakarta.persistence.*;

@Entity
@Table(name="granted_permission")
public class GrantedPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name="operation_id")
    private Operation operation;


    public GrantedPermission() {

    }

    public Long getId() {
        return id;
    }

    public GrantedPermission(Role role, Operation operation) {
        this.role = role;
        this.operation = operation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
