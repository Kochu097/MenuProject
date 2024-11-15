package menu.menubackend.datalayer.dao.impl;

import menu.menubackend.datalayer.dao.BasicDAO;
import menu.menubackend.datalayer.dao.ProductDAO;
import menu.menubackend.datalayer.entity.ProductEntity;

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
