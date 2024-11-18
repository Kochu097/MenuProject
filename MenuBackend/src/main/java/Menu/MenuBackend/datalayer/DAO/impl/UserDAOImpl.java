package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.UserDAO;
import Menu.MenuBackend.datalayer.entity.User;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl extends BasicDAO implements UserDAO {

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public Optional<User> findById(Integer id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }

    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    public boolean existsByAuthenticationToken(String authenticationToken) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.authenticationToken = :token", Long.class);
        query.setParameter("token", authenticationToken);
        return query.getSingleResult() > 0;
    }

}
