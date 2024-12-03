package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.RecipeNotFoundException;
import Menu.MenuBackend.datalayer.enums.Difficulty;
import Menu.MenuBackend.servicelayer.dto.RecipeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Test
    @DisplayName("Create Recipe - Successfully creates a new recipe with all details")
    void testCreateRecipe_Success() {
        // Arrange
        RecipeDTO recipeDTO = createTestRecipeDTO("Test Recipe");

        // Act
        RecipeDTO createdRecipe = recipeService.createRecipe(recipeDTO);

        // Assert
        assertNotNull(createdRecipe);
        assertEquals("Test Recipe", createdRecipe.getName());
        assertEquals("Test description for Test Recipe", createdRecipe.getDescription());
        assertEquals("http://test.com/recipe-image.jpg", createdRecipe.getImageUrl());
        assertEquals(LocalTime.of(0, 45), createdRecipe.getPreparationTime());
        assertEquals(BigDecimal.valueOf(4), createdRecipe.getServings());
        assertEquals("Medium", createdRecipe.getDifficulty());
    }

    @Test
    @DisplayName("Get All Recipes - Retrieves all created recipes")
    void testGetAllRecipes() {
        // Arrange
        recipeService.createRecipe(createTestRecipeDTO("Recipe 1"));
        recipeService.createRecipe(createTestRecipeDTO("Recipe 2"));

        // Act
        List<RecipeDTO> recipes = recipeService.getAllRecipes();

        // Assert
        assertEquals(2, recipes.size());
    }

    @Test
    @DisplayName("Get Recipe By ID - Successfully retrieves a recipe by its ID")
    void testGetRecipeById_Success() {
        // Arrange
        RecipeDTO createdRecipe = recipeService.createRecipe(createTestRecipeDTO("Specific Recipe"));
        Integer recipeId = createdRecipe.getId();

        // Act
        RecipeDTO retrievedRecipe = recipeService.getRecipeById(recipeId);

        // Assert
        assertNotNull(retrievedRecipe);
        assertEquals("Specific Recipe", retrievedRecipe.getName());
    }

    @Test
    @DisplayName("Get Recipe By ID - Throws exception when recipe is not found")
    void testGetRecipeById_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.getRecipeById(9999);
        });
    }

    @Test
    @DisplayName("Update Recipe - Successfully updates all recipe details")
    void testUpdateRecipe_Success() {
        // Arrange
        RecipeDTO createdRecipe = recipeService.createRecipe(createTestRecipeDTO("Original Recipe"));
        Integer recipeId = createdRecipe.getId();

        // Prepare update details
        RecipeDTO updateDTO = createTestRecipeDTO("Updated Recipe");
        updateDTO.setDescription("Updated recipe description");
        updateDTO.setPreparationTime(LocalTime.of(1, 30)); // 1 hour 30 minutes
        updateDTO.setServings(BigDecimal.valueOf(6));
        updateDTO.setDifficulty(Difficulty.HARD);

        // Act
        RecipeDTO updatedRecipe = recipeService.updateRecipe(recipeId, updateDTO);

        // Assert
        assertNotNull(updatedRecipe);
        assertEquals("Updated Recipe", updatedRecipe.getName());
        assertEquals("Updated recipe description", updatedRecipe.getDescription());
        assertEquals(LocalTime.of(1, 30), updatedRecipe.getPreparationTime());
        assertEquals(BigDecimal.valueOf(6), updatedRecipe.getServings());
        assertEquals(Difficulty.HARD, updatedRecipe.getDifficulty());
    }

    @Test
    @DisplayName("Update Recipe - Throws exception when recipe ID does not exist")
    void testUpdateRecipe_NotFound_ThrowsException() {
        // Arrange
        RecipeDTO updateDTO = createTestRecipeDTO("Non-existent Recipe");

        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.updateRecipe(9999, updateDTO);
        });
    }

    @Test
    @DisplayName("Delete Recipe - Successfully deletes an existing recipe")
    void testDeleteRecipe_Success() {
        // Arrange
        RecipeDTO createdRecipe = recipeService.createRecipe(createTestRecipeDTO("Delete Recipe"));
        Integer recipeId = createdRecipe.getId();

        // Act
        recipeService.deleteRecipe(recipeId);

        // Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.getRecipeById(recipeId);
        });
    }

    @Test
    @DisplayName("Delete Recipe - Throws exception when trying to delete non-existent recipe")
    void testDeleteRecipe_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.deleteRecipe(9999);
        });
    }
}