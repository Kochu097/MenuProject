package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.DuplicateAuthTokenException;
import Menu.MenuBackend.common.exception.UserNotFoundException;
import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceIntegrationTest extends BaseServiceIntegrationTest {

    @Test
    @DisplayName("Create User - Successfully creates a new user with unique authentication token")
    void testCreateUser_Success() {
        // Arrange
        UserDTO userDTO = createTestUserDTO("test-user-1");

        // Act
        UserDTO createdUser = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("test-user-1", createdUser.getFirebaseUserId());
    }

    @Test
    @DisplayName("Create User - Throws exception when trying to create user with duplicate authentication token")
    void testCreateUser_DuplicateAuthToken_ThrowsException() {
        // Arrange
        UserDTO userDTO1 = createTestUserDTO("duplicate-token");
        userService.createUser(userDTO1);

        // Act & Assert
        UserDTO userDTO2 = createTestUserDTO("duplicate-token");
        assertThrows(DuplicateAuthTokenException.class, () -> {
            userService.createUser(userDTO2);
        });
    }

    @Test
    @DisplayName("Get All Users - Retrieves all created users")
    void testGetAllUsers() {
        // Arrange
        userService.createUser(createTestUserDTO("user1"));
        userService.createUser(createTestUserDTO("user2"));

        // Act
        List<UserDTO> users = userService.getAllUsers();

        // Assert
        assertEquals(3, users.size()); // 3 because one user is created in the base test
        assertTrue(users.stream().anyMatch(u -> u.getFirebaseUserId().equals("user1")));
        assertTrue(users.stream().anyMatch(u -> u.getFirebaseUserId().equals("user2")));
    }

    @Test
    @DisplayName("Get User By ID - Successfully retrieves a user by their ID")
    void testGetUserById_Success() {
        // Arrange
        UserDTO userDTO = userService.createUser(createTestUserDTO("test-user"));

        // Act
        UserDTO retrievedUser = userService.getUserById(userDTO.getId());

        // Assert
        assertNotNull(retrievedUser);
        assertEquals("test-user", retrievedUser.getFirebaseUserId());
    }

    @Test
    @DisplayName("Get User By ID - Throws exception when user is not found")
    void testGetUserById_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(9999);
        });
    }

    @Test
    @DisplayName("Update User - Successfully updates user's authentication token")
    void testUpdateUser_Success() {
        // Arrange
        UserDTO userDTO = userService.createUser(createTestUserDTO("original-user"));

        // Act
        UserDTO updatedUserDTO = createTestUserDTO("updated-user");
        UserDTO result = userService.updateUser(userDTO.getId(), updatedUserDTO);

        // Assert
        assertNotNull(result);
        assertEquals("updated-user", result.getFirebaseUserId());
    }

    @Test
    @DisplayName("Update User - Throws exception when updating to an existing authentication token")
    void testUpdateUser_DuplicateAuthToken_ThrowsException() {
        // Arrange
        userService.createUser(createTestUserDTO("existing-user"));
        UserDTO userDTO = userService.createUser(createTestUserDTO("original-user"));

        // Act & Assert
        UserDTO updateDTO = createTestUserDTO("existing-user");
        assertThrows(DuplicateAuthTokenException.class, () -> {
            userService.updateUser(userDTO.getId(), updateDTO);
        });
    }

    @Test
    @DisplayName("Delete User - Successfully deletes a user")
    void testDeleteUser_Success() {
        // Arrange
        UserDTO userDTO = userService.createUser(createTestUserDTO("delete-user"));

        // Act
        userService.deleteUser(userDTO.getId());

        // Assert
        assertThrows(UserNotFoundException.class, () ->
            userService.getUserById(userDTO.getId()));
    }

    @Test
    @DisplayName("Get User By Authentication Token - Successfully retrieves user by token")
    void testGetUserByAuthenticationToken_Success() {
        // Arrange
        userService.createUser(createTestUserDTO("token-user"));

        // Act
        UserDTO retrievedUser = userService.getUserByAuthenticationToken("token-user");

        // Assert
        assertNotNull(retrievedUser);
        assertEquals("token-user", retrievedUser.getFirebaseUserId());
    }

    @Test
    @DisplayName("Get User By Authentication Token - Throws exception when token is not found")
    void testGetUserByAuthenticationToken_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByAuthenticationToken("non-existent-token");
        });
    }
}