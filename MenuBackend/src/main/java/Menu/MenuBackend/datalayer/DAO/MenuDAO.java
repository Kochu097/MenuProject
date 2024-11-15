package Menu.MenuBackend.datalayer.dao;

import Menu.MenuBackend.datalayer.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuDAO {
    /**
     * Retrieves all Menu entities from the database.
     *
     * @return a list of all Menu entities
     */
    List<Menu> findAll();

    /**
     * Retrieves a Menu entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Menu entity
     * @return an Optional containing the Menu entity if found, or an empty Optional if not found
     */
    Optional<Menu> findById(Integer id);

    /**
     * Saves a new Menu entity or updates an existing one.
     *
     * @param menu the Menu entity to be saved or updated
     * @return the saved or updated Menu entity
     */
    Menu save(Menu menu);

    /**
     * Deletes a Menu entity from the database.
     *
     * @param menu the Menu entity to be deleted
     */
    void delete(Menu menu);
}
