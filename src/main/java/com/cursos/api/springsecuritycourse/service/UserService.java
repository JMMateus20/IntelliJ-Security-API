package com.cursos.api.springsecuritycourse.service;

import com.cursos.api.springsecuritycourse.dto.SaveUser;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.User;

import java.util.Optional;

public interface UserService {
    User registrOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);
}
