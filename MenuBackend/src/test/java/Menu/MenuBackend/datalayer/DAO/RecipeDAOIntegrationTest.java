package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

class RecipeDAOIntegrationTest extends BaseDAOIntegrationTest {

    private Recipe testRecipe;

    @Override
    public void beforeEachTest() {
        testRecipe = new Recipe();
        testRecipe.setName("Test Recipe");
        testRecipe.setDescription("Test Recipe Description");
        testRecipe.setPreparationTime(LocalTime.of(0, 30));
        testRecipe.setServings(BigDecimal.valueOf(4));
        testRecipe.setDifficulty("Medium");
        testRecipe = recipeDAO.save(testRecipe);
    }

    @Test
    @DisplayName("Should successfully create a new recipe")
    void testCreatingRecipe() {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("New Recipe");
        newRecipe.setDescription("New Recipe Description");
        newRecipe.setPreparationTime(LocalTime.of(1, 0));
        newRecipe.setServings(BigDecimal.valueOf(2));
        newRecipe.setDifficulty("Hard");

        Recipe savedRecipe = recipeDAO.save(newRecipe);

        Assertions.assertNotNull(savedRecipe.getId());
        Assertions.assertEquals(newRecipe.getName(), savedRecipe.getName());
    }

    @Test
    @DisplayName("Should successfully update an existing recipe")
    void testUpdatingRecipe() {
        testRecipe.setName("Updated Recipe");
        testRecipe.setDifficulty("Easy");

        Recipe updatedRecipe = recipeDAO.save(testRecipe);

        Assertions.assertEquals(testRecipe.getId(), updatedRecipe.getId());
        Assertions.assertEquals("Updated Recipe", updatedRecipe.getName());
    }

    @Test
    @DisplayName("Should return all recipes from the database")
    void testFindAll() {
        Recipe secondRecipe = new Recipe();
        secondRecipe.setName("Second Test Recipe");
        secondRecipe.setServings(BigDecimal.valueOf(3));
        recipeDAO.save(secondRecipe);

        List<Recipe> recipes = recipeDAO.findAll();

        Assertions.assertTrue(recipes.size() >= 2);
    }

    @Test
    @DisplayName("Should find recipe by existing ID")
    void testFindById() {
        Optional<Recipe> foundRecipe = recipeDAO.findById(testRecipe.getId());

        Assertions.assertTrue(foundRecipe.isPresent());
        Assertions.assertEquals(testRecipe.getId(), foundRecipe.get().getId());
    }

    @Test
    @DisplayName("Should return empty optional when finding recipe by non-existent ID")
    void testFindByIdNonExistent() {
        Optional<Recipe> foundRecipe = recipeDAO.findById(-1);

        Assertions.assertTrue(foundRecipe.isEmpty());
    }

    @Test
    @DisplayName("Should successfully delete an existing recipe")
    void testDelete() {
        Recipe recipeToDelete = new Recipe();
        recipeToDelete.setName("Recipe to Delete");
        recipeToDelete.setServings(BigDecimal.valueOf(1));
        recipeToDelete = recipeDAO.save(recipeToDelete);

        recipeDAO.delete(recipeToDelete);

        Optional<Recipe> deletedRecipe = recipeDAO.findById(recipeToDelete.getId());
        Assertions.assertTrue(deletedRecipe.isEmpty());
    }
}