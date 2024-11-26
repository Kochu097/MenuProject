package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.IngredientDAO;
import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.entity.MenuItem;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientDAOImpl extends BasicDAO implements IngredientDAO {

    @Override
    public List<MenuItem> findAll() {
        TypedQuery<MenuItem> query = entityManager.createQuery("SELECT i FROM MenuItem i", MenuItem.class);
        return query.getResultList();
    }

    @Override
    public Optional<MenuItem> findById(Integer id) {
        MenuItem menuItem = entityManager.find(MenuItem.class, id);
        return Optional.ofNullable(menuItem);
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        if (menuItem.getId() == null) {
            entityManager.persist(menuItem);
        } else {
            menuItem = entityManager.merge(menuItem);
        }
        return menuItem;
    }

    @Override
    public void delete(MenuItem menuItem) {
        entityManager.remove(entityManager.contains(menuItem) ? menuItem : entityManager.merge(menuItem));
    }

    @Override
    public List<MenuItem> findByMenuId(Integer menuId) {
        TypedQuery<MenuItem> query = entityManager.createQuery(
                "SELECT i FROM MenuItem i WHERE i.menu.id = :menuId", MenuItem.class);
        query.setParameter("menuId", menuId);
        return query.getResultList();
    }
}