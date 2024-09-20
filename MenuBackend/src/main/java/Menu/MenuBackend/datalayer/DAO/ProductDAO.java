package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.ProductEntity;

public interface ProductDAO {

    ProductEntity saveProduct(ProductEntity product);

    ProductEntity getProductById(int id);
}
