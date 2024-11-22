package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemDAO {
    /**
     * Retrieves all Ingredient entities from the database.
     *
     * @return a list of all Ingredient entities
     */
    List<MenuItem> findAll();

    /**
     * Retrieves an Ingredient entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Ingredient entity
     * @return an Optional containing the Ingredient entity if found, or an empty Optional if not found
     */
    Optional<MenuItem> findById(Integer id);

    /**
     * Saves a new Ingredient entity or updates an existing one.
     *
     * @param menuItem the Ingredient entity to be saved or updated
     * @return the saved or updated Ingredient entity
     */
    MenuItem save(MenuItem menuItem);

    /**
     * Deletes an Ingredient entity from the database.
     *
     * @param menuItem the Ingredient entity to be deleted
     */
    void delete(MenuItem menuItem);

    /**
     * Retrieves all Ingredient entities associated with a specific menu.
     *
     * @param menuId the unique identifier (ID) of the Menu
     * @return a list of Ingredient entities associated with the specified menu
     */
    List<MenuItem> findByMenuId(Integer menuId);
}