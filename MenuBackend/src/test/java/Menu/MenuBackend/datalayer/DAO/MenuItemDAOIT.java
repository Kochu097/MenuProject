package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.DAO.impl.MenuItemDAOImpl;
import Menu.MenuBackend.datalayer.entity.*;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(FirebaseAuthMockConfig.class)
class MenuItemDAOIT extends BaseDAOIT {
    @Autowired
    private MenuItemDAOImpl menuItemDAO;

    private User testUser;
    private Menu testMenu;
    private Recipe testRecipe;
    private Product testProduct;
    private MenuItem testMenuItem;

    @Override
    public void beforeEachTest() {
            // Create and persist test user
            testUser = createUser("test-auth-token");
            //entityManager.flush();

            // Create and persist test menu
            testMenu = new Menu();
            testMenu.setDay(LocalDate.now());
            testMenu.setUser(testUser);
            //entityManager.persist(testMenu);
            //entityManager.flush();

            // Create and persist test product
            testProduct = new Product();
            testProduct.setName("Test Product");
            testProduct.setCalories(100L);
            testProduct.setWeight(200L);
            testProduct.setWeightUnit(WeightUnit.GRAM);
            testProduct.setDescription("Test product description");
            testProduct.setImageUrl("test-image-url");
            //entityManager.persist(testProduct);
            //entityManager.flush();

            // Create and persist test recipe
            testRecipe = new Recipe();
            testRecipe.setName("Test Recipe");
            testRecipe.setDescription("Test recipe description");
            testRecipe.setImageUrl("test-recipe-image");
            testRecipe.setPreparationTime(LocalTime.of(1, 30));
            testRecipe.setServings(BigDecimal.valueOf(4));
            testRecipe.setDifficulty("MEDIUM");
//            testRecipe.getIngredients().add(testProduct);
            //entityManager.persist(testRecipe);
            //entityManager.flush();

            // Create and persist test ingredient
            testMenuItem = new MenuItem();
            testMenuItem.setMenu(testMenu);
            testMenuItem.setRecipe(testRecipe);
            testMenuItem.setProduct(testProduct);
            //entityManager.persist(testMenuItem);
            //entityManager.flush();
    }

    @Test
    void findAll_ShouldReturnAllIngredients() {
        // When
        List<MenuItem> menuItems = menuItemDAO.findAll();

        // Then
        assertThat(menuItems).isNotEmpty();
        assertThat(menuItems).contains(testMenuItem);
    }

    @Test
    void findById_WithExistingId_ShouldReturnIngredient() {
        // When
        Optional<MenuItem> found = menuItemDAO.findById(testMenuItem.getId());

        // Then
        assertThat(found).isPresent();
        MenuItem menuItem = found.get();
        assertThat(menuItem.getMenu().getDay()).isEqualTo(testMenu.getDay());
        assertThat(menuItem.getMenu().getUser()).isEqualTo(testUser);
        assertThat(menuItem.getRecipe()).isEqualTo(testRecipe);
        assertThat(menuItem.getProduct()).isEqualTo(testProduct);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        // When
        Optional<MenuItem> found = menuItemDAO.findById(-1);

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void save_NewIngredient_ShouldPersistAndReturnIngredient() {
        // Given
        Menu newMenu = new Menu();
        newMenu.setDay(LocalDate.now().plusDays(1));
        newMenu.setUser(testUser);
        //entityManager.persist(newMenu);

        Recipe newRecipe = new Recipe();
        newRecipe.setName("New Recipe");
        newRecipe.setServings(BigDecimal.valueOf(2));
        //entityManager.persist(newRecipe);

        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setCalories(150L);
        //entityManager.persist(newProduct);

        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setMenu(newMenu);
        newMenuItem.setRecipe(newRecipe);
        newMenuItem.setProduct(newProduct);

        // When
        MenuItem saved = menuItemDAO.save(newMenuItem);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMenu()).isEqualTo(newMenu);
        assertThat(saved.getRecipe()).isEqualTo(newRecipe);
        assertThat(saved.getProduct()).isEqualTo(newProduct);

        // Verify persistence
//        MenuItem persisted = entityManager.find(MenuItem.class, saved.getId());
//        assertThat(persisted).isNotNull();
//        assertThat(persisted.getMenu()).isEqualTo(newMenu);
//        assertThat(persisted.getRecipe()).isEqualTo(newRecipe);
//        assertThat(persisted.getProduct()).isEqualTo(newProduct);
    }

//    @Test
//    void save_ExistingIngredient_ShouldUpdateAndReturnIngredient() {
//        // Given
//        Recipe newRecipe = new Recipe();
//        newRecipe.setName("Updated Recipe");
//        newRecipe.setServings(BigDecimal.valueOf(3));
//        //entityManager.persist(newRecipe);
//
//        testMenuItem.setRecipe(newRecipe);
//
//        // When
//        MenuItem updated = menuItemDAO.save(testMenuItem);
//
//        // Then
//        assertThat(updated.getRecipe()).isEqualTo(newRecipe);
//        assertThat(updated.getMenu()).isEqualTo(testMenu);
//        assertThat(updated.getProduct()).isEqualTo(testProduct);
//
//        // Verify persistence
//        MenuItem persisted = entityManager.find(MenuItem.class, testMenuItem.getId());
//        assertThat(persisted.getRecipe()).isEqualTo(newRecipe);
//    }

//    @Test
//    void delete_ShouldRemoveIngredient() {
//        // When
//        menuItemDAO.delete(testMenuItem);
//        //entityManager.flush();
//
//        // Then
//        MenuItem deleted = entityManager.find(MenuItem.class, testMenuItem.getId());
//        assertThat(deleted).isNull();
//    }

    @Test
    void findByMenuId_ShouldReturnIngredientsForMenu() {
        // When
        List<MenuItem> menuItems = menuItemDAO.findByMenuId(testMenu.getId());

        // Then
        assertThat(menuItems).hasSize(1);
        assertThat(menuItems.get(0)).isEqualTo(testMenuItem);
        assertThat(menuItems.get(0).getMenu().getDay()).isEqualTo(testMenu.getDay());
        assertThat(menuItems.get(0).getRecipe()).isEqualTo(testRecipe);
        assertThat(menuItems.get(0).getProduct()).isEqualTo(testProduct);
    }

    @Test
    void findByMenuId_WithNonExistingMenuId_ShouldReturnEmptyList() {
        // When
        List<MenuItem> menuItems = menuItemDAO.findByMenuId(-1);

        // Then
        assertThat(menuItems).isEmpty();
    }
}