package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.MenuNotFoundException;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    /**
     * Retrieves all Menu entities.
     *
     * @return a list of MenuDTO objects representing all Menu entities
     */
    List<MenuDTO> getAllMenus();

    /**
     * Retrieves a Menu entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Menu entity
     * @return a MenuDTO object representing the Menu entity
     * @throws MenuNotFoundException if the Menu entity is not found
     */
    MenuDTO getMenuById(Integer id) throws MenuNotFoundException;

    /**
     * Creates a new Menu entity.
     *
     * @param menuDTO a MenuDTO object containing the data for the new Menu entity
     * @return a MenuDTO object representing the newly created Menu entity
     */
    MenuDTO createMenu(MenuDTO menuDTO);

    /**
     * Updates an existing Menu entity.
     *
     * @param id the unique identifier (ID) of the Menu entity to be updated
     * @param menuDTO a MenuDTO object containing the updated data for the Menu entity
     * @return a MenuDTO object representing the updated Menu entity
     * @throws MenuNotFoundException if the Menu entity is not found
     */
    MenuDTO updateMenu(Integer id, MenuDTO menuDTO) throws MenuNotFoundException;

    /**
     * Deletes a Menu entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Menu entity to be deleted
     * @throws MenuNotFoundException if the Menu entity is not found
     */
    void deleteMenu(Integer id) throws MenuNotFoundException;
}
