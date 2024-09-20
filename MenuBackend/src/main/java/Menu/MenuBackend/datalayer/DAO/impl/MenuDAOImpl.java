package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.MenuDAO;
import Menu.MenuBackend.datalayer.entity.MenuEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MenuDAOImpl extends BasicDAO implements MenuDAO {
    @Override
    public MenuEntity saveMenu(MenuEntity menu) {
        entityManager.persist(menu);
        return menu;
    }

    @Override
    public MenuEntity updateMenu(MenuEntity menu) {
        return entityManager.merge(menu);
    }

    @Override
    public MenuEntity getMenuById(Long id) {
        return entityManager.find(MenuEntity.class, id);
    }
}
