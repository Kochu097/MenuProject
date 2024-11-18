package Menu.MenuBackend.datalayer.DAO.impl;

import jakarta.persistence.TypedQuery;
import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.MenuDAO;
import Menu.MenuBackend.datalayer.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MenuDAOImpl extends BasicDAO implements MenuDAO {

    @Override
    public List<Menu> findAll() {
        TypedQuery<Menu> query = entityManager.createQuery("SELECT m FROM Menu m", Menu.class);
        return query.getResultList();
    }

    @Override
    public Optional<Menu> findById(Integer id) {
        Menu menu = entityManager.find(Menu.class, id);
        return Optional.ofNullable(menu);
    }

    @Override
    public Menu save(Menu menu) {
        if (menu.getId() == null) {
            entityManager.persist(menu);
        } else {
            menu = entityManager.merge(menu);
        }
        return menu;
    }

    @Override
    public void delete(Menu menu) {
        entityManager.remove(entityManager.contains(menu) ? menu : entityManager.merge(menu));
    }
}
