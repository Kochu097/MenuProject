package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.ProductDAO;
import Menu.MenuBackend.datalayer.entity.ProductEntity;
import jakarta.persistence.EntityManager;

public class ProductDAOImpl extends BasicDAO implements ProductDAO {
    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        entityManager.persist(product);
        return product;
    }

    @Override
    public ProductEntity getProductById(int id) {
        return entityManager.find(ProductEntity.class, id);
    }
}
