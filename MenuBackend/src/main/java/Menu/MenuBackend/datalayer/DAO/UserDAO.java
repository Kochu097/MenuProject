package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.UserEntity;

public interface UserDAO {

    UserEntity saveUser(UserEntity user);

    UserEntity geUserById(int id);
}
