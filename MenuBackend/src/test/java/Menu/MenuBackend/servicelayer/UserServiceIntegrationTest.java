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

    private UserDTO createTestUserDTO(String firebaseUserId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirebaseUserId(firebaseUserId);
        return userDTO;
    }

    @Test
    @DisplayName("Create User - Successfully creates a new user with unique authentication token")
    void testCreateUser_Success() {
        // Arrange
        UserDTO userDTO = createTestUserDTO("test-user-1");

        // Act
        UserDTO createdUser = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        assertEquals("test-user-1", createdUser.getFirebaseUserId());
        assertTrue(userDAO.existsByFirebaseUserId("test-user-1"));
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
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Get User By ID - Successfully retrieves a user by their ID")
    void testGetUserById_Success() {
        // Arrange
        userService.createUser(createTestUserDTO("test-user"));
        Optional<User> userDTOOptional = userDAO.findByFirebaseUserId("test-user");
        assertFalse(userDTOOptional.isEmpty());
        Integer userId = userDTOOptional.get().getId();

        // Act
        UserDTO retrievedUser = userService.getUserById(userId);

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
        userService.createUser(createTestUserDTO("original-user"));
        Integer userId = userDAO.findByFirebaseUserId("original-user").get().getId();

        // Act
        UserDTO updatedUserDTO = createTestUserDTO("updated-user");
        UserDTO result = userService.updateUser(userId, updatedUserDTO);

        // Assert
        assertNotNull(result);
        assertEquals("updated-user", result.getFirebaseUserId());
    }

    @Test
    @DisplayName("Update User - Throws exception when updating to an existing authentication token")
    void testUpdateUser_DuplicateAuthToken_ThrowsException() {
        // Arrange
        userService.createUser(createTestUserDTO("existing-user"));
        userService.createUser(createTestUserDTO("original-user"));
        Integer userId = userDAO.findByFirebaseUserId("original-user").get().getId();

        // Act & Assert
        UserDTO updateDTO = createTestUserDTO("existing-user");
        assertThrows(DuplicateAuthTokenException.class, () -> {
            userService.updateUser(userId, updateDTO);
        });
    }

    @Test
    @DisplayName("Delete User - Successfully deletes a user")
    void testDeleteUser_Success() {
        // Arrange
        userService.createUser(createTestUserDTO("delete-user"));
        Integer userId = userDAO.findByFirebaseUserId("delete-user").get().getId();

        // Act
        userService.deleteUser(userId);

        // Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
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