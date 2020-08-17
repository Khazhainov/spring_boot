package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {

    List<User> listUsers();

    User add(User user);

    User getById(Long id);

    void deleteById(Long id);

    Role getByRoleName(String roleName);
}
