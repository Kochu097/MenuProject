package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.*;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

class IngredientDAOIntegrationTest extends BaseDAOIntegrationTest {

    private MenuItem testMenuItem;
    private Menu testMenu;
    private Product testProduct;
    private Recipe testRecipe;

    @Override
    public void beforeEachTest() {
        // Create a test user
        User testUser = new User();
        testUser.setFirebaseUserId("test-user-id");
        testUser = userDAO.save(testUser);

        // Create a test menu
        testMenu = new Menu();
        testMenu.setUser(testUser);
        testMenu.setDay(LocalDate.now());
        testMenu = menuDAO.save(testMenu);

        // Create a test product
        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Product Description");
        testProduct.setWeight(100L);
        testProduct.setWeightUnit(WeightUnit.GRAM);
        testProduct.setCalories(50L);
        testProduct = productDAO.save(testProduct);

        // Create a test recipe
        testRecipe = new Recipe();
        testRecipe.setName("Test Recipe");
        testRecipe.setDescription("Test Recipe Description");
        testRecipe.setPreparationTime(LocalTime.of(0, 30));
        testRecipe.setServings(BigDecimal.valueOf(4));
        testRecipe.setDifficulty("Medium");
        testRecipe = recipeDAO.save(testRecipe);

        // Create a test menu item
        testMenuItem = new MenuItem();
        testMenuItem.setMenu(testMenu);
        testMenuItem.setProduct(testProduct);
        testMenuItem.setRecipe(testRecipe);
        testMenuItem = menuItemDAO.save(testMenuItem);
    }

    @Test
    @DisplayName("Should successfully create a new menu item")
    void testCreatingMenuItem() {
        MenuItem expectedMenuItem = new MenuItem();
        expectedMenuItem.setMenu(testMenu);
        expectedMenuItem.setProduct(testProduct);
        expectedMenuItem.setRecipe(testRecipe);

        MenuItem actualMenuItem = menuItemDAO.save(expectedMenuItem);

        Assertions.assertNotNull(actualMenuItem.getId());
        Assertions.assertEquals(testMenu, actualMenuItem.getMenu());
        Assertions.assertEquals(testProduct, actualMenuItem.getProduct());
        Assertions.assertEquals(testRecipe, actualMenuItem.getRecipe());
    }

    @Test
    @DisplayName("Should successfully update an existing menu item")
    void testUpdatingMenuItem() {
        // Create a new product and recipe to update with
        Product newProduct = new Product();
        newProduct.setName("New Test Product");
        newProduct.setDescription("New Test Product Description");
        newProduct.setWeight(200L);
        newProduct.setWeightUnit(WeightUnit.KILOGRAM);
        newProduct.setCalories(100L);
        newProduct = productDAO.save(newProduct);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("New Test Recipe");
        newRecipe.setDescription("New Test Recipe Description");
        newRecipe.setPreparationTime(LocalTime.of(1, 0));
        newRecipe.setServings(BigDecimal.valueOf(2));
        newRecipe.setDifficulty("Hard");
        newRecipe = recipeDAO.save(newRecipe);

        // Update the existing menu item
        testMenuItem.setProduct(newProduct);
        testMenuItem.setRecipe(newRecipe);
        MenuItem updatedMenuItem = menuItemDAO.save(testMenuItem);

        Assertions.assertEquals(testMenuItem.getId(), updatedMenuItem.getId());
        Assertions.assertEquals(newProduct, updatedMenuItem.getProduct());
        Assertions.assertEquals(newRecipe, updatedMenuItem.getRecipe());
    }

    @Test
    @DisplayName("Should return all menu items from the database")
    void testFindAll() {
        // Create another menu item to ensure we have multiple records
        MenuItem secondMenuItem = new MenuItem();
        secondMenuItem.setMenu(testMenu);
        secondMenuItem.setProduct(testProduct);
        secondMenuItem.setRecipe(testRecipe);
        menuItemDAO.save(secondMenuItem);

        List<MenuItem> menuItems = menuItemDAO.findAll();

        Assertions.assertTrue(menuItems.size() >= 2);
    }

    @Test
    @DisplayName("Should find menu item by existing ID")
    void testFindById() {
        Optional<MenuItem> foundMenuItem = menuItemDAO.findById(testMenuItem.getId());

        Assertions.assertTrue(foundMenuItem.isPresent());
        Assertions.assertEquals(testMenuItem.getId(), foundMenuItem.get().getId());
    }

    @Test
    @DisplayName("Should return empty optional when finding menu item by non-existent ID")
    void testFindByIdNonExistent() {
        Optional<MenuItem> foundMenuItem = menuItemDAO.findById(-1);

        Assertions.assertTrue(foundMenuItem.isEmpty());
    }

    @Test
    @DisplayName("Should successfully delete an existing menu item")
    void testDelete() {
        // Create a menu item to delete
        MenuItem menuItemToDelete = new MenuItem();
        menuItemToDelete.setMenu(testMenu);
        menuItemToDelete.setProduct(testProduct);
        menuItemToDelete.setRecipe(testRecipe);
        menuItemToDelete = menuItemDAO.save(menuItemToDelete);

        // Delete the menu item
        menuItemDAO.delete(menuItemToDelete);

        // Verify the menu item is deleted
        Optional<MenuItem> deletedMenuItem = menuItemDAO.findById(menuItemToDelete.getId());
        Assertions.assertTrue(deletedMenuItem.isEmpty());
    }

    @Test
    @DisplayName("Should find menu items by menu ID")
    void testFindByMenuId() {
        // Create multiple menu items for the same menu
        MenuItem secondMenuItem = new MenuItem();
        secondMenuItem.setMenu(testMenu);
        secondMenuItem.setProduct(testProduct);
        secondMenuItem.setRecipe(testRecipe);
        menuItemDAO.save(secondMenuItem);

        List<MenuItem> menuItems = menuItemDAO.findByMenuId(testMenu.getId());

        Assertions.assertEquals(2, menuItems.size());
        Assertions.assertTrue(menuItems.stream()
                .allMatch(item -> item.getMenu().getId().equals(testMenu.getId())));
    }

    @Test
    @DisplayName("Should return empty list when finding menu items by non-existent menu ID")
    void testFindByMenuIdNonExistent() {
        List<MenuItem> menuItems = menuItemDAO.findByMenuId(-1);

        Assertions.assertTrue(menuItems.isEmpty());
    }
}