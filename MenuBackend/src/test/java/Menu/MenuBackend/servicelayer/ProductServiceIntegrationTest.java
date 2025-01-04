package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.common.exception.ProductNotFoundException;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import Menu.MenuBackend.servicelayer.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceIntegrationTest extends BaseServiceIntegrationTest{

    @Test
    @DisplayName("Create Product - Successfully creates a new product with all details")
    void testCreateProduct_Success() {
        // Arrange
        ProductDTO productDTO = createTestProductDTO("Test Product");

        // Act
        ProductDTO createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals("Test description for Test Product", createdProduct.getDescription());
        assertEquals("http://test.com/image.jpg", createdProduct.getImageUrl());
        assertEquals(100L, createdProduct.getWeight());
        assertEquals(WeightUnit.GRAM, createdProduct.getWeightUnit());
        assertEquals(250L, createdProduct.getCalories());
    }

    @Test
    @DisplayName("Get All Products - Retrieves all created products")
    void testGetAllProducts() {
        // Arrange
        productService.createProduct(createTestProductDTO("Product 1"));
        productService.createProduct(createTestProductDTO("Product 2"));

        // Act
        List<ProductDTO> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Get Product By ID - Successfully retrieves a product by its ID")
    void testGetProductById_Success() {
        // Arrange
        ProductDTO createdProduct = productService.createProduct(createTestProductDTO("Specific Product"));
        Integer productId = createdProduct.getId();

        // Act
        ProductDTO retrievedProduct = productService.getProductById(productId);

        // Assert
        assertNotNull(retrievedProduct);
        assertEquals("Specific Product", retrievedProduct.getName());
    }

    @Test
    @DisplayName("Get Product By ID - Throws exception when product is not found")
    void testGetProductById_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(9999);
        });
    }

    @Test
    @DisplayName("Update Product - Successfully updates all product details")
    void testUpdateProduct_Success() {
        // Arrange
        ProductDTO createdProduct = productService.createProduct(createTestProductDTO("Original Product"));
        Integer productId = createdProduct.getId();

        // Prepare update details
        ProductDTO updateDTO = createTestProductDTO("Updated Product");
        updateDTO.setDescription("Updated description");
        updateDTO.setWeight(200D);
        updateDTO.setWeightUnit(WeightUnit.KILOGRAM);
        updateDTO.setCalories(500L);

        // Act
        ProductDTO updatedProduct = productService.updateProduct(productId, updateDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated description", updatedProduct.getDescription());
        assertEquals(200L, updatedProduct.getWeight());
        assertEquals(WeightUnit.KILOGRAM, updatedProduct.getWeightUnit());
        assertEquals(500L, updatedProduct.getCalories());
    }

    @Test
    @DisplayName("Update Product - Throws exception when product ID does not exist")
    void testUpdateProduct_NotFound_ThrowsException() {
        // Arrange
        ProductDTO updateDTO = createTestProductDTO("Non-existent Product");

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(9999, updateDTO);
        });
    }

    @Test
    @DisplayName("Delete Product - Successfully deletes an existing product")
    void testDeleteProduct_Success() {
        // Arrange
        ProductDTO createdProduct = productService.createProduct(createTestProductDTO("Delete Product"));
        Integer productId = createdProduct.getId();

        // Act
        productService.deleteProduct(productId);

        // Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    @DisplayName("Delete Product - Throws exception when trying to delete non-existent product")
    void testDeleteProduct_NotFound_ThrowsException() {
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(9999);
        });
    }
}