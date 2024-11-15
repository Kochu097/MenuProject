package menu.menubackend.datalayer.dao;

import menu.menubackend.datalayer.entity.RecipeEntity;

public interface RecipeDAO {

    RecipeEntity saveMeal(RecipeEntity meal);

    RecipeEntity getMealByID(Long id);

}
