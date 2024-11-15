package menu.menubackend.datalayer.dao.impl;

import menu.menubackend.datalayer.dao.BasicDAO;
import menu.menubackend.datalayer.dao.RecipeDAO;
import menu.menubackend.datalayer.entity.RecipeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDAOImpl extends BasicDAO implements RecipeDAO {

    @Override
    public RecipeEntity saveMeal(RecipeEntity meal) {
        entityManager.persist(meal);
        return meal;
    }

    @Override
    public RecipeEntity getMealByID(Long id) {
        return entityManager.find(RecipeEntity.class, id);
    }

}
