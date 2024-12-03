package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.*;
import Menu.MenuBackend.datalayer.enums.Difficulty;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


class IngredientDAOIntegrationTest extends BaseDAOIntegrationTest {

    @BeforeEach
    public void beforeEach() {
        Product product1 = new Product();
        product1.setName("Product 1");
        productDAO.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        productDAO.save(product2);

        Product product3 = new Product();
        product3.setName("Product 3");
        productDAO.save(product3);

        Recipe recipe1 = new Recipe();
        recipe1.setName("Recipe 1");
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.setPreparationTime(LocalTime.of(0, 30));
        recipeDAO.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Recipe 2");
        recipe2.setDifficulty(Difficulty.MEDIUM);
        recipe2.setPreparationTime(LocalTime.of(1, 0));
        recipeDAO.save(recipe2);

        Recipe recipe3 = new Recipe();
        recipe3.setName("Recipe 3");
        recipe3.setDifficulty(Difficulty.HARD);
        recipe3.setPreparationTime(LocalTime.of(2, 0));
        recipeDAO.save(recipe3);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setProduct(productDAO.findById(1).get());
        ingredient1.setRecipe(recipeDAO.findById(1).get());
        ingredientDAO.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setProduct(productDAO.findById(2).get());
        ingredient2.setRecipe(recipeDAO.findById(2).get());
        ingredientDAO.save(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setProduct(productDAO.findById(3).get());
        ingredient3.setRecipe(recipeDAO.findById(3).get());
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
        Optional<Ingredient> ingredient = ingredientDAO.findById(1);
        Assertions.assertTrue(ingredient.isPresent());
        Assertions.assertEquals(1, ingredient.get().getId());
    }


}