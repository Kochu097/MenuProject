package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.IngredientDAO;
import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.entity.Ingredient;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientDAOImpl extends BasicDAO implements IngredientDAO {

    @Override
    public List<Ingredient> findAll() {
        TypedQuery<Ingredient> query = entityManager.createQuery("SELECT i FROM Ingredient i", Ingredient.class);
        return query.getResultList();
    }

    @Override
    public Optional<Ingredient> findById(Integer id) {
        Ingredient ingredient = entityManager.find(Ingredient.class, id);
        return Optional.ofNullable(ingredient);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (ingredient.getId() == null) {
            entityManager.persist(ingredient);
        } else {
            ingredient = entityManager.merge(ingredient);
        }
        return ingredient;
    }

    @Override
    public void delete(Ingredient ingredient) {
        entityManager.remove(entityManager.contains(ingredient) ? ingredient : entityManager.merge(ingredient));
    }
}