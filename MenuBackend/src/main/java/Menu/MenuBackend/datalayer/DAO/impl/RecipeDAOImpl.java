package Menu.MenuBackend.datalayer.DAO.impl;

import jakarta.persistence.TypedQuery;
import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.RecipeDAO;
import Menu.MenuBackend.datalayer.entity.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RecipeDAOImpl extends BasicDAO implements RecipeDAO {

    @Override
    public List<Recipe> findAll() {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r", Recipe.class);
        return query.getResultList();
    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        Recipe recipe = entityManager.find(Recipe.class, id);
        return Optional.ofNullable(recipe);
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (recipe.getId() == null) {
            entityManager.persist(recipe);
        } else {
            recipe = entityManager.merge(recipe);
        }
        return recipe;
    }

    @Override
    public void delete(Recipe recipe) {
        entityManager.remove(entityManager.contains(recipe) ? recipe : entityManager.merge(recipe));
    }
}
