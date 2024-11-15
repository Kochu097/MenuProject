package menu.menubackend.datalayer.dao.impl;

import menu.menubackend.datalayer.dao.BasicDAO;
import menu.menubackend.datalayer.dao.MenuDAO;
import menu.menubackend.datalayer.entity.MenuEntity;
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
