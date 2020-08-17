package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> listUsers() {
        return em.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public User add(User user) {
        if (user.getId() == null) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public User getById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        return (User) em
                .createQuery("SELECT u FROM User u WHERE u.email LIKE :email")
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        em.remove(getById(id));
    }

    @Override
    public Role getByRoleName(String role) {
        return (Role) em
                .createQuery("SELECT r FROM Role r WHERE r.role LIKE :role")
                .setParameter("role", role)
                .getSingleResult();
    }
}
