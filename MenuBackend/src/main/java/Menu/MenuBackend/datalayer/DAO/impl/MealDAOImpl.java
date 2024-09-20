package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.DAO.BasicDAO;
import Menu.MenuBackend.datalayer.DAO.MealDAO;
import Menu.MenuBackend.datalayer.entity.MealEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MealDAOImpl extends BasicDAO implements MealDAO {

    @Override
    public MealEntity saveMeal(MealEntity meal) {
        entityManager.persist(meal);
        return meal;
    }

    @Override
    public MealEntity getMealByID(Long id) {
        return entityManager.find(MealEntity.class, id);
    }

}
