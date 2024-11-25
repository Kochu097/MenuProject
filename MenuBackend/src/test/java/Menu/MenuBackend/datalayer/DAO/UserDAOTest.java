package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.DAO.impl.UserDAOImpl;
import Menu.MenuBackend.datalayer.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class UserDAOTest extends BaseDAOTest{

    @Autowired
    public UserDAOImpl userDAO;

    private User testUser;

    @Override
    public void beforeEachTest() {
        // Create a test user before each test
        testUser = new User();
        testUser.setFirebaseUserId("test-firebase-id");
        testUser = userDAO.save(testUser);
    }

    @Test
    @DisplayName("Should successfully create a new user")
    void testCreatingUser() {
        User expectedUser = new User();
        expectedUser.setFirebaseUserId("test-id");

        User actualUser = userDAO.save(expectedUser);

        Assertions.assertEquals(actualUser.getFirebaseUserId(), expectedUser.getFirebaseUserId());
        Assertions.assertNotNull(actualUser.getId());
    }

    @Test
    @DisplayName("Should successfully update an existing user")
    void testUpdatingUser() {
        // Update the existing user
        testUser.setFirebaseUserId("updated-firebase-id");
        User updatedUser = userDAO.save(testUser);

        Assertions.assertEquals("updated-firebase-id", updatedUser.getFirebaseUserId());
        Assertions.assertEquals(testUser.getId(), updatedUser.getId());
    }

    @Test
    @DisplayName("Should return all users from the database")
    void testFindAll() {
        // Create another user to ensure we have multiple records
        User secondUser = new User();
        secondUser.setFirebaseUserId("second-test-id");
        userDAO.save(secondUser);

        List<User> users = userDAO.findAll();

        Assertions.assertTrue(users.size() >= 2);
        Assertions.assertTrue(users.stream()
                .anyMatch(user -> user.getFirebaseUserId().equals("test-firebase-id")));
        Assertions.assertTrue(users.stream()
                .anyMatch(user -> user.getFirebaseUserId().equals("second-test-id")));
    }

    @Test
    @DisplayName("Should find user by existing ID")
    void testFindById() {
        Optional<User> foundUser = userDAO.findById(testUser.getId());

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(testUser.getFirebaseUserId(), foundUser.get().getFirebaseUserId());
    }

    @Test
    @DisplayName("Should return empty optional when finding user by non-existent ID")
    void testFindByIdNonExistent() {
        Optional<User> foundUser = userDAO.findById(-1);

        Assertions.assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("Should successfully delete an existing user")
    void testDelete() {
        // Create a user to delete
        User userToDelete = new User();
        userToDelete.setFirebaseUserId("to-delete");
        userToDelete = userDAO.save(userToDelete);

        // Delete the user
        userDAO.delete(userToDelete);

        // Verify the user is deleted
        Optional<User> deletedUser = userDAO.findById(userToDelete.getId());
        Assertions.assertTrue(deletedUser.isEmpty());
    }

    @Test
    @DisplayName("Should confirm user exists when Firebase User ID is valid")
    void testExistsByFirebaseUserId() {
        boolean exists = userDAO.existsByFirebaseUserId(testUser.getFirebaseUserId());

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false when checking non-existent Firebase User ID")
    void testExistsByFirebaseUserIdNonExistent() {
        boolean exists = userDAO.existsByFirebaseUserId("non-existent-token");

        Assertions.assertFalse(exists);
    }

    @Test
    @DisplayName("Should find user by valid Firebase User ID")
    void testFindByFirebaseUserId() {
        Optional<User> foundUser = userDAO.findByFirebaseUserId(testUser.getFirebaseUserId());

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(testUser.getFirebaseUserId(), foundUser.get().getFirebaseUserId());
    }

    @Test
    @DisplayName("Should return empty optional when finding user by non-existent Firebase user ID")
    void testFindByFirebaseUserIdNonExistent() {
        Optional<User> foundUser = userDAO.findByFirebaseUserId("non-existent-token");

        Assertions.assertTrue(foundUser.isEmpty());
    }
}