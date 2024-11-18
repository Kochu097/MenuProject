package Menu.MenuBackend.datalayer.DAO.impl;

import jakarta.persistence.TypedQuery;
import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.ProductDAO;
import Menu.MenuBackend.datalayer.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDAOImpl extends BasicDAO implements ProductDAO {
    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            entityManager.persist(product);
        } else {
            product = entityManager.merge(product);
        }
        return product;
    }

    @Override
    public void delete(Product product) {
        entityManager.remove(entityManager.contains(product) ? product : entityManager.merge(product));
    }
}
