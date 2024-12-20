package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.entity.User;
import jakarta.persistence.TypedQuery;
import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.ProductDAO;
import Menu.MenuBackend.datalayer.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDAOImpl extends BasicDAO implements ProductDAO {
    @Override
    @Transactional
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Product> findAllForUser(User user) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.user = :user OR p.shared", Product.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<Product> findById(Integer id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        if (product.getId() == null) {
            entityManager.persist(product);
        } else {
            product = entityManager.merge(product);
        }
        return product;
    }

    @Override
    @Transactional
    public void delete(Product product) {
        entityManager.remove(entityManager.contains(product) ? product : entityManager.merge(product));
    }
}
