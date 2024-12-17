package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.ProductNotFoundException;
import Menu.MenuBackend.servicelayer.dto.ProductDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    /**
     * Retrieves all Product entities.
     *
     * @return a list of ProductDTO objects representing all Product entities
     */
    List<ProductDTO> getAllProducts();

    List<ProductDTO> getAllProductsForUser(UserDTO user);

    /**
     * Retrieves a Product entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Product entity
     * @return a ProductDTO object representing the Product entity
     * @throws ProductNotFoundException if the Product entity is not found
     */
    ProductDTO getProductById(Integer id) throws ProductNotFoundException;

    /**
     * Creates a new Product entity.
     *
     * @param productDTO a ProductDTO object containing the data for the new Product entity
     * @return a ProductDTO object representing the newly created Product entity
     */
    ProductDTO createProduct(ProductDTO productDTO);

    /**
     * Updates an existing Product entity.
     *
     * @param id the unique identifier (ID) of the Product entity to be updated
     * @param productDTO a ProductDTO object containing the updated data for the Product entity
     * @return a ProductDTO object representing the updated Product entity
     * @throws ProductNotFoundException if the Product entity is not found
     */
    ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws ProductNotFoundException;

    /**
     * Deletes a Product entity by its unique identifier (ID).
     *
     * @param id the unique identifier (ID) of the Product entity to be deleted
     * @throws ProductNotFoundException if the Product entity is not found
     */
    void deleteProduct(Integer id) throws ProductNotFoundException;

    void addProduct(ProductDTO productDTO, MultipartFile image, UserDTO user);
}