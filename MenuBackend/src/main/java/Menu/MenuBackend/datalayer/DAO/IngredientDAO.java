package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientDAO {
    /**
     * Retrieves all Ingredient entities from the database.
     *
     * @return a list of all Ingredient entities
     */
    List<Ingredient> findAll();

    /**
     * Retrieves an Ingredient entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Ingredient entity
     * @return an Optional containing the Ingredient entity if found, or an empty Optional if not found
     */
    Optional<Ingredient> findById(Integer id);

    /**
     * Saves a new Ingredient entity or updates an existing one.
     *
     * @param ingredient the Ingredient entity to be saved or updated
     * @return the saved or updated Ingredient entity
     */
    Ingredient save(Ingredient ingredient);

    /**
     * Deletes an Ingredient entity from the database.
     *
     * @param ingredient the Ingredient entity to be deleted
     */
    void delete(Ingredient ingredient);
}