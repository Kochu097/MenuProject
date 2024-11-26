package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.MenuNotFoundException;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Test
    @DisplayName("Create Menu - Successfully creates a new menu with all details")
    void testCreateMenu_Success() {
        // Arrange
        MenuDTO menuDTO = createTestMenuDTO(testUser);

        // Act
        MenuDTO createdMenu = menuService.createMenu(menuDTO);

        // Assert
        assertNotNull(createdMenu);
        assertNotNull(createdMenu.getId());
        assertEquals(menuDTO.getDay(), createdMenu.getDay());
        assertEquals(testUser.getId(), createdMenu.getUser().getId());
    }

    @Test
    @DisplayName("Get All Menus - Retrieves all created menus")
    void testGetAllMenus() {
        // Arrange
        menuService.createMenu(createTestMenuDTO(testUser));
        menuService.createMenu(createTestMenuDTO(testUser));

        // Act
        List<MenuDTO> menus = menuService.getAllMenus();

        // Assert
        assertTrue(menus.size() >= 2);
    }

    @Test
    @DisplayName("Get Menu By ID - Successfully retrieves a menu by its ID")
    void testGetMenuById_Success() {
        // Arrange
        MenuDTO createdMenu = menuService.createMenu(createTestMenuDTO(testUser));
        Integer menuId = createdMenu.getId();

        // Act
        MenuDTO retrievedMenu = menuService.getMenuById(menuId);

        // Assert
        assertNotNull(retrievedMenu);
        assertEquals(menuId, retrievedMenu.getId());
        assertEquals(createdMenu.getDay(), retrievedMenu.getDay());
        assertEquals(testUser.getId(), retrievedMenu.getUser().getId());
    }

    @Test
    @DisplayName("Get Menu By ID - Throws exception when menu is not found")
    void testGetMenuById_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(MenuNotFoundException.class, () -> {
            menuService.getMenuById(9999);
        });
    }

    @Test
    @DisplayName("Update Menu - Successfully updates menu details")
    void testUpdateMenu_Success() {
        // Arrange
        MenuDTO createdMenu = menuService.createMenu(createTestMenuDTO(testUser));
        Integer menuId = createdMenu.getId();

        // Prepare update details
        MenuDTO updateDTO = new MenuDTO();
        updateDTO.setDay(LocalDate.now().plusDays(1));
        updateDTO.setUser(testUser);

        // Act
        MenuDTO updatedMenu = menuService.updateMenu(menuId, updateDTO);

        // Assert
        assertNotNull(updatedMenu);
        assertEquals(menuId, updatedMenu.getId());
        assertEquals(updateDTO.getDay(), updatedMenu.getDay());
        assertEquals(testUser.getId(), updatedMenu.getUser().getId());
    }

    @Test
    @DisplayName("Update Menu - Throws exception when menu ID does not exist")
    void testUpdateMenu_NotFound_ThrowsException() {
        // Arrange
        MenuDTO updateDTO = createTestMenuDTO(testUser);

        // Act & Assert
        assertThrows(MenuNotFoundException.class, () -> {
            menuService.updateMenu(9999, updateDTO);
        });
    }

    @Test
    @DisplayName("Delete Menu - Successfully deletes an existing menu")
    void testDeleteMenu_Success() {
        // Arrange
        MenuDTO createdMenu = menuService.createMenu(createTestMenuDTO(testUser));
        Integer menuId = createdMenu.getId();

        // Act
        menuService.deleteMenu(menuId);

        // Assert
        assertThrows(MenuNotFoundException.class, () -> {
            menuService.getMenuById(menuId);
        });
    }

    @Test
    @DisplayName("Delete Menu - Throws exception when trying to delete non-existent menu")
    void testDeleteMenu_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(MenuNotFoundException.class, () -> {
            menuService.deleteMenu(9999);
        });
    }

    @Test
    @DisplayName("Get Menu For Period - Successfully retrieves menus for a specific period")
    void testGetMenuForPeriod_Success() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        // Create multiple menus within the date range
        menuService.createMenu(createTestMenuDTO());
        menuService.createMenu(createTestMenuDTO());

        // Act
        List<MenuDTO> periodMenus = menuService.getMenuForPeriod(startDate, endDate, testUser);

        // Assert
        assertNotNull(periodMenus);
        assertTrue(periodMenus.size() >= 2);
        assertTrue(periodMenus.stream()
                .allMatch(menu -> !menu.getDay().isBefore(startDate) && !menu.getDay().isAfter(endDate)));
    }
}