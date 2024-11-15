package menu.menubackend.servicelayer;

import menu.menubackend.common.exception.DuplicateAuthTokenException;
import menu.menubackend.common.exception.UserNotFoundException;
import menu.menubackend.servicelayer.dto.UserDTO;

import java.util.List;

/**
 * Service interface for managing User entities.
 * This interface defines the business logic methods for performing CRUD operations
 * and other user-related tasks.
 */
public interface UserService {
    /**
     * Retrieves all User entities.
     *
     * @return a list of UserDTO objects representing all User entities
     */
    List<UserDTO> getAllUsers();

    /**
     * Retrieves a User entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the User entity
     * @return a UserDTO object representing the User entity
     * @throws UserNotFoundException if the User entity is not found
     */
    UserDTO getUserById(Integer id) throws UserNotFoundException;

    /**
     * Creates a new User entity.
     *
     * @param userDTO a UserDTO object containing the data for the new User entity
     * @return a UserDTO object representing the newly created User entity
     * @throws DuplicateAuthTokenException if the provided authentication token already exists
     */
    UserDTO createUser(UserDTO userDTO) throws DuplicateAuthTokenException;

    /**
     * Updates an existing User entity.
     *
     * @param id the unique identifier (ID) of the User entity to be updated
     * @param userDTO a UserDTO object containing the updated data for the User entity
     * @return a UserDTO object representing the updated User entity
     * @throws UserNotFoundException if the User entity is not found
     * @throws DuplicateAuthTokenException if the provided authentication token already exists
     */
    UserDTO updateUser(Integer id, UserDTO userDTO) throws UserNotFoundException, DuplicateAuthTokenException;

    /**
     * Deletes a User entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the User entity to be deleted
     * @throws UserNotFoundException if the User entity is not found
     */
    void deleteUser(Integer id) throws UserNotFoundException;
}
