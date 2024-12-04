package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.BaseIntegrationTest;
import Menu.MenuBackend.datalayer.enums.AmountUnit;
import Menu.MenuBackend.datalayer.enums.Difficulty;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import Menu.MenuBackend.servicelayer.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class BaseServiceIntegrationTest extends BaseIntegrationTest {

    protected static final LocalDate TODAY = LocalDate.now();

    @Autowired
    protected UserService userService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected RecipeService recipeService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected IngredientService ingredientService;

    protected UserDTO testUser;

    @BeforeEach
    void beforeEach() {
        testUser = userService.createUser(createTestUserDTO("ValidUserID"));
    }

    @AfterEach
    void afterEach() {
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
        }
    }

    protected IngredientDTO createTestIngredientDTO(RecipeDTO recipe, ProductDTO product) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setRecipe(recipe);
        ingredientDTO.setProduct(product);
        ingredientDTO.setAmount(100L);
        ingredientDTO.setAmountUnit(AmountUnit.GRAMS);
        return ingredientDTO;
    }

    protected RecipeDTO createTestRecipeDTO(String name) {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName(name);
        recipeDTO.setDescription("Test description for " + name);
        recipeDTO.setImageUrl("http://test.com/recipe-image.jpg");
        recipeDTO.setPreparationTime(LocalTime.of(0, 45)); // 45 minutes
        recipeDTO.setServings(BigDecimal.valueOf(4));
        recipeDTO.setDifficulty(Difficulty.EASY);
        recipeDTO.setSource("test-source");
        recipeDTO.setShared(false);
        return recipeDTO;
    }

    protected ProductDTO createTestProductDTO(String name) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription("Test description for " + name);
        productDTO.setImageUrl("http://test.com/image.jpg");
        productDTO.setWeight(100L);
        productDTO.setWeightUnit(WeightUnit.GRAM);
        productDTO.setCalories(250L);
        productDTO.setSource("test-source");
        productDTO.setShared(false);
        return productDTO;
    }

    protected UserDTO createTestUserDTO(String firebaseUserId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirebaseUserId(firebaseUserId);
        return userDTO;
    }

    protected MenuDTO createTestMenuDTO() {
        return createTestMenuDTO(TODAY, testUser);
    }

    protected MenuDTO createTestMenuDTO(UserDTO user) {
        return createTestMenuDTO(TODAY, user);
    }

    protected MenuDTO createTestMenuDTO(LocalDate date, UserDTO user) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setDay(date);
        menuDTO.setUser(user);
        return menuDTO;
    }
}
