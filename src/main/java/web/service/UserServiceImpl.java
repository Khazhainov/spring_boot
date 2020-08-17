package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public User add(User user) {
        return userDao.add(user);
    }

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Role getByRoleName(String roleName) {
        return userDao.getByRoleName(roleName);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.getByEmail(email);
    }


}
