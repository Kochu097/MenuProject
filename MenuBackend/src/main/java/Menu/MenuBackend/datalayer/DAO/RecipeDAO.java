package Menu.MenuBackend.datalayer.dao;

import Menu.MenuBackend.datalayer.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeDAO {
    /**
     * Retrieves all Recipe entities from the database.
     *
     * @return a list of all Recipe entities
     */
    List<Recipe> findAll();

    /**
     * Retrieves a Recipe entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Recipe entity
     * @return an Optional containing the Recipe entity if found, or an empty Optional if not found
     */
    Optional<Recipe> findById(Integer id);

    /**
     * Saves a new Recipe entity or updates an existing one.
     *
     * @param recipe the Recipe entity to be saved or updated
     * @return the saved or updated Recipe entity
     */
    Recipe save(Recipe recipe);

    /**
     * Deletes a Recipe entity from the database.
     *
     * @param recipe the Recipe entity to be deleted
     */
    void delete(Recipe recipe);
}
