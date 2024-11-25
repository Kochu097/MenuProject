package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.DAO.impl.MenuDAOImpl;
import Menu.MenuBackend.datalayer.DAO.impl.UserDAOImpl;
import Menu.MenuBackend.datalayer.entity.Menu;
import Menu.MenuBackend.datalayer.entity.User;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MenuDAOTest extends BaseDAOTest {

    @Autowired
    public MenuDAOImpl menuDAO;

    @Autowired
    public UserDAOImpl userDAO;

    private Menu testMenu;
    private User testUser;

    @Override
    public void beforeEachTest() {
        // Create a test user
        testUser = new User();
        testUser.setFirebaseUserId("test-user-id");
        testUser = userDAO.save(testUser);

        // Create a test menu
        testMenu = new Menu();
        testMenu.setUser(testUser);
        testMenu.setDay(LocalDate.now());
        testMenu = menuDAO.save(testMenu);
    }

    @Test
    @DisplayName("Should successfully create a new menu")
    void testCreatingMenu() {
        Menu expectedMenu = new Menu();
        expectedMenu.setUser(testUser);
        expectedMenu.setDay(LocalDate.now().plusDays(1));

        Menu actualMenu = menuDAO.save(expectedMenu);

        Assertions.assertEquals(expectedMenu.getDay(), actualMenu.getDay());
        Assertions.assertNotNull(actualMenu.getId());
    }

    @Test
    @DisplayName("Should return all menus from the database")
    void testFindAll() {
        // Create another menu to ensure we have multiple records
        Menu secondMenu = new Menu();
        secondMenu.setUser(testUser);
        secondMenu.setDay(LocalDate.now().plusDays(2));
        menuDAO.save(secondMenu);

        List<Menu> menus = menuDAO.findAll();

        Assertions.assertTrue(menus.size() >= 2);
        Assertions.assertTrue(menus.stream()
                .anyMatch(menu -> menu.getDay().equals(LocalDate.now())));
        Assertions.assertTrue(menus.stream()
                .anyMatch(menu -> menu.getDay().equals(LocalDate.now().plusDays(2))));
    }

    @Test
    @DisplayName("Should find menu by existing ID")
    void testFindById() {
        Optional<Menu> foundMenu = menuDAO.findById(testMenu.getId());

        Assertions.assertTrue(foundMenu.isPresent());
        Assertions.assertEquals(testMenu.getDay(), foundMenu.get().getDay());
    }

    @Test
    @DisplayName("Should return empty optional when finding menu by non-existent ID")
    void testFindByIdNonExistent() {
        Optional<Menu> foundMenu = menuDAO.findById(-1);

        Assertions.assertTrue(foundMenu.isEmpty());
    }

    @Test
    @DisplayName("Should successfully delete an existing menu")
    void testDelete() {
        // Create a menu to delete
        Menu menuToDelete = new Menu();
        menuToDelete.setUser(testUser);
        menuToDelete.setDay(LocalDate.now().plusDays(3));
        menuToDelete = menuDAO.save(menuToDelete);

        // Delete the menu
        menuDAO.delete(menuToDelete);

        // Verify the menu is deleted
        Optional<Menu> deletedMenu = menuDAO.findById(menuToDelete.getId());
        Assertions.assertTrue(deletedMenu.isEmpty());
    }

    @Test
    @DisplayName("Should retrieve menus within a specified date range for a user")
    void testGetByPeriod() {
        // Create multiple menus with different dates
        Menu menu1 = new Menu();
        menu1.setUser(testUser);
        menu1.setDay(LocalDate.now().minusDays(5));
        menuDAO.save(menu1);

        Menu menu2 = new Menu();
        menu2.setUser(testUser);
        menu2.setDay(LocalDate.now().minusDays(2));
        menuDAO.save(menu2);

        Menu menu3 = new Menu();
        menu3.setUser(testUser);
        menu3.setDay(LocalDate.now().plusDays(2));
        menuDAO.save(menu3);

        // Retrieve menus within a date range
        List<Menu> menusInRange = menuDAO.getByPeriod(
                LocalDate.now().minusDays(3),
                LocalDate.now().plusDays(3),
                testUser
        );

        Assertions.assertEquals(3, menusInRange.size());
        Assertions.assertTrue(menusInRange.stream()
                .anyMatch(menu -> menu.getDay().equals(LocalDate.now().plusDays(2))));
        Assertions.assertTrue(menusInRange.stream()
                .anyMatch(menu -> menu.getDay().equals(LocalDate.now().minusDays(2))));
    }

    @Test
    @DisplayName("Should return empty list when no menus exist in specified date range")
    void testGetByPeriodNoMenus() {
        List<Menu> menusInRange = menuDAO.getByPeriod(
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(5),
                testUser
        );

        Assertions.assertTrue(menusInRange.isEmpty());
    }
}