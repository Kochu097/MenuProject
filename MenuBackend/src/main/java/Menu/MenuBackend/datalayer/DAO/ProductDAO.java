package Menu.MenuBackend.datalayer.dao;

import Menu.MenuBackend.datalayer.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    /**
     * Retrieves all Product entities from the database.
     *
     * @return a list of all Product entities
     */
    List<Product> findAll();

    /**
     * Retrieves a Product entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Product entity
     * @return an Optional containing the Product entity if found, or an empty Optional if not found
     */
    Optional<Product> findById(Integer id);

    /**
     * Saves a new Product entity or updates an existing one.
     *
     * @param product the Product entity to be saved or updated
     * @return the saved or updated Product entity
     */
    Product save(Product product);

    /**
     * Deletes a Product entity from the database.
     *
     * @param product the Product entity to be deleted
     */
    void delete(Product product);
}
