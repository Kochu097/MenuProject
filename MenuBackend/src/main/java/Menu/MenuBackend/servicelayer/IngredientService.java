package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.IngredientNotFoundException;
import Menu.MenuBackend.servicelayer.dto.IngredientDTO;

import java.util.List;

public interface IngredientService {
    /**
     * Retrieves all Ingredient entities.
     *
     * @return a list of IngredientDTO objects representing all Ingredient entities
     */
    List<IngredientDTO> getAllIngredients();

    /**
     * Retrieves an Ingredient entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Ingredient entity
     * @return an IngredientDTO object representing the Ingredient entity
     * @throws IngredientNotFoundException if the Ingredient entity is not found
     */
    IngredientDTO getIngredientById(Integer id) throws IngredientNotFoundException;

    /**
     * Creates a new Ingredient entity.
     *
     * @param ingredientDTO an IngredientDTO object containing the data for the new Ingredient entity
     * @return an IngredientDTO object representing the newly created Ingredient entity
     */
    IngredientDTO createIngredient(IngredientDTO ingredientDTO);

    /**
     * Updates an existing Ingredient entity.
     *
     * @param id the unique identifier (ID) of the Ingredient entity to be updated
     * @param ingredientDTO an IngredientDTO object containing the updated data for the Ingredient entity
     * @return an IngredientDTO object representing the updated Ingredient entity
     * @throws IngredientNotFoundException if the Ingredient entity is not found
     */
    IngredientDTO updateIngredient(Integer id, IngredientDTO ingredientDTO) throws IngredientNotFoundException;

    /**
     * Deletes an Ingredient entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Ingredient entity to be deleted
     * @throws IngredientNotFoundException if the Ingredient entity is not found
     */
    void deleteIngredient(Integer id) throws IngredientNotFoundException;
}
