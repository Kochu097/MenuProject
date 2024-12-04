package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.IngredientNotFoundException;
import Menu.MenuBackend.servicelayer.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Test
    @DisplayName("Create Ingredient - Successfully creates a new ingredient with all details")
    void testCreateIngredient_Success() {
        // Arrange
        RecipeDTO testRecipe = recipeService.createRecipe(createTestRecipeDTO("Test Recipe"));
        ProductDTO testProduct = productService.createProduct(createTestProductDTO("Test Product"));

        IngredientDTO ingredientDTO = createTestIngredientDTO(testRecipe, testProduct);

        // Act
        IngredientDTO createdIngredient = ingredientService.createIngredient(ingredientDTO);

        // Assert
        assertNotNull(createdIngredient);
        assertNotNull(createdIngredient.getId());
        assertEquals(testRecipe.getId(), createdIngredient.getRecipe().getId());
        assertEquals(testProduct.getId(), createdIngredient.getProduct().getId());
    }

    @Test
    @DisplayName("Get All Ingredients - Retrieves all created ingredients")
    void testGetAllIngredients() {
        // Arrange
        RecipeDTO testRecipe = recipeService.createRecipe(createTestRecipeDTO("Test Recipe"));
        ProductDTO testProduct1 = productService.createProduct(createTestProductDTO("Test Product 1"));
        ProductDTO testProduct2 = productService.createProduct(createTestProductDTO("Test Product 2"));

        ingredientService.createIngredient(createTestIngredientDTO(testRecipe, testProduct1));
        ingredientService.createIngredient(createTestIngredientDTO(testRecipe, testProduct2));

        // Act
        List<IngredientDTO> ingredients = ingredientService.getAllIngredients();

        // Assert
        assertTrue(ingredients.size() >= 2);
    }

    @Test
    @DisplayName("Get Ingredient By ID - Successfully retrieves an ingredient by its ID")
    void testGetIngredientById_Success() {
        // Arrange
        RecipeDTO testRecipe = recipeService.createRecipe(createTestRecipeDTO("Test Recipe"));
        ProductDTO testProduct = productService.createProduct(createTestProductDTO("Test Product"));

        IngredientDTO createdIngredient = ingredientService.createIngredient(
                createTestIngredientDTO(testRecipe, testProduct)
        );
        Integer ingredientId = createdIngredient.getId();

        // Act
        IngredientDTO retrievedIngredient = ingredientService.getIngredientById(ingredientId);

        // Assert
        assertNotNull(retrievedIngredient);
        assertEquals(ingredientId, retrievedIngredient.getId());
        assertEquals(testRecipe.getId(), retrievedIngredient.getRecipe().getId());
        assertEquals(testProduct.getId(), retrievedIngredient.getProduct().getId());
    }

    @Test
    @DisplayName("Get Ingredient By ID - Throws exception when ingredient is not found")
    void testGetIngredientById_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.getIngredientById(9999);
        });
    }

    @Test
    @DisplayName("Update Ingredient - Successfully updates ingredient details")
    void testUpdateIngredient_Success() {
        // Arrange
        RecipeDTO testRecipe1 = recipeService.createRecipe(createTestRecipeDTO("Original Recipe"));
        ProductDTO testProduct1 = productService.createProduct(createTestProductDTO("Original Product"));

        IngredientDTO createdIngredient = ingredientService.createIngredient(
                createTestIngredientDTO(testRecipe1, testProduct1)
        );
        Integer ingredientId = createdIngredient.getId();

        // Prepare update details
        RecipeDTO testRecipe2 = recipeService.createRecipe(createTestRecipeDTO("Updated Recipe"));
        ProductDTO testProduct2 = productService.createProduct(createTestProductDTO("Updated Product"));

        IngredientDTO updateDTO = createTestIngredientDTO(testRecipe2, testProduct2);

        // Act
        IngredientDTO updatedIngredient = ingredientService.updateIngredient(ingredientId, updateDTO);

        // Assert
        assertNotNull(updatedIngredient);
        assertEquals(ingredientId, updatedIngredient.getId());
        assertEquals(testRecipe2.getId(), updatedIngredient.getRecipe().getId());
        assertEquals(testProduct2.getId(), updatedIngredient.getProduct().getId());
    }

    @Test
    @DisplayName("Update Ingredient - Throws exception when ingredient ID does not exist")
    void testUpdateIngredient_NotFound_ThrowsException() {
        // Arrange
        RecipeDTO testRecipe = recipeService.createRecipe(createTestRecipeDTO("Test Recipe"));
        ProductDTO testProduct = productService.createProduct(createTestProductDTO("Test Product"));

        IngredientDTO updateDTO = createTestIngredientDTO(testRecipe, testProduct);

        // Act & Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.updateIngredient(9999, updateDTO);
        });
    }

    @Test
    @DisplayName("Delete Ingredient - Successfully deletes an existing ingredient")
    void testDeleteIngredient_Success() {
        // Arrange
        RecipeDTO testRecipe = recipeService.createRecipe(createTestRecipeDTO("Test Recipe"));
        ProductDTO testProduct = productService.createProduct(createTestProductDTO("Test Product"));

        IngredientDTO createdIngredient = ingredientService.createIngredient(
                createTestIngredientDTO(testRecipe, testProduct)
        );
        Integer ingredientId = createdIngredient.getId();

        // Act
        ingredientService.deleteIngredient(ingredientId);

        // Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.getIngredientById(ingredientId);
        });
    }

    @Test
    @DisplayName("Delete Ingredient - Throws exception when trying to delete non-existent ingredient")
    void testDeleteIngredient_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.deleteIngredient(9999);
        });
    }
}