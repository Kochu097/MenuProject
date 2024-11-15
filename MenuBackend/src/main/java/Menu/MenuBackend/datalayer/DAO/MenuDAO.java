package menu.menubackend.datalayer.dao;

import menu.menubackend.datalayer.entity.MenuEntity;

public interface MenuDAO {

    MenuEntity saveMenu(MenuEntity menu);

    MenuEntity updateMenu(MenuEntity menu);

    MenuEntity getMenuById(Long id);

}
