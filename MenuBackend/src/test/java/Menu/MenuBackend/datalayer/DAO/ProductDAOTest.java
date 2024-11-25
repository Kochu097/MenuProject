package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.DAO.impl.ProductDAOImpl;
import Menu.MenuBackend.datalayer.entity.Product;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class ProductDAOTest extends BaseDAOTest {

    @Autowired
    private ProductDAOImpl productDAO;

    private Product testProduct;

    @Override
    public void beforeEachTest() {
        // Create a test product
        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Product Description");
        testProduct.setWeight(100L);
        testProduct.setWeightUnit(WeightUnit.GRAM);
        testProduct.setCalories(50L);
        testProduct = productDAO.save(testProduct);
    }

    @Test
    @DisplayName("Should successfully create a new product")
    void testCreatingProduct() {
        Product newProduct = new Product();
        newProduct.setName("New Product");
        newProduct.setDescription("New Product Description");
        newProduct.setWeight(200L);
        newProduct.setWeightUnit(WeightUnit.KILOGRAM);
        newProduct.setCalories(100L);

        Product savedProduct = productDAO.save(newProduct);

        Assertions.assertNotNull(savedProduct.getId());
        Assertions.assertEquals(newProduct.getName(), savedProduct.getName());
        Assertions.assertEquals(newProduct.getDescription(), savedProduct.getDescription());
    }

    @Test
    @DisplayName("Should successfully update an existing product")
    void testUpdatingProduct() {
        testProduct.setName("Updated Product");
        testProduct.setCalories(75L);

        Product updatedProduct = productDAO.save(testProduct);

        Assertions.assertEquals(testProduct.getId(), updatedProduct.getId());
        Assertions.assertEquals("Updated Product", updatedProduct.getName());
        Assertions.assertEquals(75L, updatedProduct.getCalories());
    }

    @Test
    @DisplayName("Should return all products from the database")
    void testFindAll() {
        // Create another product to ensure we have multiple records
        Product secondProduct = new Product();
        secondProduct.setName("Second Test Product");
        secondProduct.setWeight(50L);
        secondProduct.setWeightUnit(WeightUnit.GRAM);
        secondProduct.setCalories(25L);
        productDAO.save(secondProduct);

        List<Product> products = productDAO.findAll();

        Assertions.assertTrue(products.size() >= 2);
    }

    @Test
    @DisplayName("Should find product by existing ID")
    void testFindById() {
        Optional<Product> foundProduct = productDAO.findById(testProduct.getId());

        Assertions.assertTrue(foundProduct.isPresent());
        Assertions.assertEquals(testProduct.getId(), foundProduct.get().getId());
    }

    @Test
    @DisplayName("Should return empty optional when finding product by non-existent ID")
    void testFindByIdNonExistent() {
        Optional<Product> foundProduct = productDAO.findById(-1);

        Assertions.assertTrue(foundProduct.isEmpty());
    }

    @Test
    @DisplayName("Should successfully delete an existing product")
    void testDelete() {
        // Create a product to delete
        Product productToDelete = new Product();
        productToDelete.setName("Product to Delete");
        productToDelete.setWeight(75L);
        productToDelete.setWeightUnit(WeightUnit.GRAM);
        productToDelete = productDAO.save(productToDelete);

        // Delete the product
        productDAO.delete(productToDelete);

        // Verify the product is deleted
        Optional<Product> deletedProduct = productDAO.findById(productToDelete.getId());
        Assertions.assertTrue(deletedProduct.isEmpty());
    }
}