package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.MenuEntity;

public interface MenuDAO {

    MenuEntity saveMenu(MenuEntity menu);

    MenuEntity updateMenu(MenuEntity menu);

    MenuEntity getMenuById(Long id);

}
