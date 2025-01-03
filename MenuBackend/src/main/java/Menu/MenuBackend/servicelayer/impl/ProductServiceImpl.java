package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.common.exception.ProductNotFoundException;
import Menu.MenuBackend.datalayer.DAO.ProductDAO;
import Menu.MenuBackend.datalayer.entity.Product;
import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.servicelayer.ProductService;
import Menu.MenuBackend.servicelayer.dto.ProductDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public List<ProductDTO> getAllProductsForUser(UserDTO user) {
        return productDAO.findAllForUser(modelMapper.map(user, User.class))
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

    @Override
    public void addProduct(ProductDTO productDTO, MultipartFile image, UserDTO user) {

        Product product = modelMapper.map(productDTO, Product.class);
        product.setUser(modelMapper.map(user, User.class));
        productDAO.save(product);
    }
}
