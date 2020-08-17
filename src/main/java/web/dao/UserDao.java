package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserDao {
    List<User> listUsers();

    User add(User user);

    User getById(Long id);

    User getByEmail(String email);

    void deleteById(Long id);

    Role getByRoleName(String roleName);
}
