package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.UserDAO;
import Menu.MenuBackend.datalayer.entity.UserEntity;

public class UserDAOImpl extends BasicDAO implements UserDAO {

    @Override
    public UserEntity saveUser(UserEntity user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public UserEntity geUserById(int id) {
        return entityManager.find(UserEntity.class, id);
    }
}
