package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.entity.MealEntity;

public interface MealDAO {

    MealEntity saveMeal(MealEntity meal);

    MealEntity getMealByID(Long id);

}
