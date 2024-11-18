package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.common.exception.ProductNotFoundException;
import Menu.MenuBackend.datalayer.DAO.ProductDAO;
import Menu.MenuBackend.datalayer.entity.Product;
import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.servicelayer.ProductService;
import Menu.MenuBackend.servicelayer.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productDAO.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer id) {
        Product product = productDAO.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        User user = productDTO.getUser() != null ? modelMapper.map(productDTO.getUser(), User.class) : null;
        product.setUser(user);
        Product savedProduct = productDAO.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Product existingProduct = productDAO.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setWeight(productDTO.getWeight());
        existingProduct.setWeightUnit(productDTO.getWeightUnit());
        existingProduct.setCalories(productDTO.getCalories());
        User user = productDTO.getUser() != null ? modelMapper.map(productDTO.getUser(), User.class) : null;
        existingProduct.setUser(user);

        Product updatedProduct = productDAO.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        Product product = productDAO.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productDAO.delete(product);
    }
}
