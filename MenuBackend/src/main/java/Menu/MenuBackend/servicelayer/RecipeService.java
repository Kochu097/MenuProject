package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.RecipeNotFoundException;
import Menu.MenuBackend.servicelayer.dto.RecipeDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    /**
     * Retrieves all Recipe entities.
     *
     * @return a list of RecipeDTO objects representing all Recipe entities
     */
    List<RecipeDTO> getAllRecipes();

    /**
     * Retrieves a Recipe entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Recipe entity
     * @return a RecipeDTO object representing the Recipe entity
     * @throws RecipeNotFoundException if the Recipe entity is not found
     */
    RecipeDTO getRecipeById(Integer id) throws RecipeNotFoundException;

    /**
     * Creates a new Recipe entity.
     *
     * @param recipeDTO a RecipeDTO object containing the data for the new Recipe entity
     * @return a RecipeDTO object representing the newly created Recipe entity
     */
    RecipeDTO createRecipe(RecipeDTO recipeDTO);

    /**
     * Updates an existing Recipe entity.
     *
     * @param id the unique identifier (ID) of the Recipe entity to be updated
     * @param recipeDTO a RecipeDTO object containing the updated data for the Recipe entity
     * @return a RecipeDTO object representing the updated Recipe entity
     * @throws RecipeNotFoundException if the Recipe entity is not found
     */
    RecipeDTO updateRecipe(Integer id, RecipeDTO recipeDTO) throws RecipeNotFoundException;

    /**
     * Deletes a Recipe entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Recipe entity to be deleted
     * @throws RecipeNotFoundException if the Recipe entity is not found
     */
    void deleteRecipe(Integer id) throws RecipeNotFoundException;

    void addRecipe(RecipeDTO recipeDTO, MultipartFile image, UserDTO user);
}