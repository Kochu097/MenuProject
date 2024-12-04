package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.*;
import Menu.MenuBackend.datalayer.enums.Difficulty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


class IngredientDAOIntegrationTest extends BaseDAOIntegrationTest {

    Product product1;

    @Override
    public void beforeEachTest() {
        product1 = new Product();
        product1.setName("Product 1");
        product1.setShared(true);
        product1.setSource("Source 1");
        productDAO.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setShared(true);
        product2.setSource("Source 2");
        productDAO.save(product2);

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setShared(true);
        product3.setSource("Source 3");
        productDAO.save(product3);

        Recipe recipe1 = new Recipe();
        recipe1.setName("Recipe 1");
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.setPreparationTime(LocalTime.of(0, 30));
        recipe1.setShared(true);
        recipe1.setSource("Source 1");
        recipeDAO.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Recipe 2");
        recipe2.setDifficulty(Difficulty.MEDIUM);
        recipe2.setPreparationTime(LocalTime.of(1, 0));
        recipe2.setShared(true);
        recipe2.setSource("Source 2");
        recipeDAO.save(recipe2);

        Recipe recipe3 = new Recipe();
        recipe3.setName("Recipe 3");
        recipe3.setDifficulty(Difficulty.HARD);
        recipe3.setPreparationTime(LocalTime.of(2, 0));
        recipe3.setShared(true);
        recipe3.setSource("Source 3");
        recipeDAO.save(recipe3);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setProduct(product1);
        ingredient1.setRecipe(recipe1);
        ingredientDAO.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setProduct(product2);
        ingredient2.setRecipe(recipe2);
        ingredientDAO.save(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setProduct(product3);
        ingredient3.setRecipe(recipe3);
        ingredientDAO.save(ingredient3);
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        List<Ingredient> ingredients = ingredientDAO.findAll();
        Assertions.assertEquals(3, ingredients.size());
    }

    @Test
    @DisplayName("Test findById")
    void testFindById() {
        Optional<Ingredient> ingredient = ingredientDAO.findById(product1.getId());
        Assertions.assertTrue(ingredient.isPresent());
        Assertions.assertEquals(product1.getId(), ingredient.get().getId());
    }


}