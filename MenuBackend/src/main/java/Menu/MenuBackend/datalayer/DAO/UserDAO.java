package Menu.MenuBackend.datalayer.dao;

import Menu.MenuBackend.datalayer.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) interface for managing User entities in the database.
 * This interface defines the basic CRUD (Create, Read, Update, Delete) operations
 * for the User entity, as well as any additional custom queries or methods.
 */
public interface UserDAO {
    /**
     * Retrieves all User entities from the database.
     *
     * @return a list of all User entities
     */
    List<User> findAll();

    /**
     * Retrieves a User entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the User entity
     * @return an Optional containing the User entity if found, or an empty Optional if not found
     */
    Optional<User> findById(Integer id);

    /**
     * Saves a new User entity or updates an existing one.
     *
     * @param user the User entity to be saved or updated
     * @return the saved or updated User entity
     */
    User save(User user);

    /**
     * Deletes a User entity from the database.
     *
     * @param user the User entity to be deleted
     */
    void delete(User user);

    /**
     * Checks if a User entity with the given authentication token already exists in the database.
     *
     * @param authenticationToken the authentication token to check
     * @return true if a User entity with the given authentication token exists, false otherwise
     */
    boolean existsByAuthenticationToken(String authenticationToken);
}
