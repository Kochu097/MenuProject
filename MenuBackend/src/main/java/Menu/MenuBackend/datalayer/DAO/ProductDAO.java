package menu.menubackend.datalayer.dao;

import menu.menubackend.datalayer.entity.ProductEntity;

public interface ProductDAO {

    ProductEntity saveProduct(ProductEntity product);

    ProductEntity getProductById(int id);
}
