package menu.menubackend.datalayer.dao.impl;

import menu.menubackend.datalayer.entity.RecipeEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class MealDAOImplIT {

    @Autowired
    private RecipeDAOImpl mealDAO;

    private static final String MEAL_NAME = "Spaghetti";

    @Test
    void testSaveMeal() {

        RecipeEntity meal = new RecipeEntity();
        meal.setName(MEAL_NAME);

        meal = mealDAO.saveMeal(meal);

        Assertions.assertEquals(MEAL_NAME, meal.getName());
        Assertions.assertNotNull(meal.getId());

    }

    @Test
    void testGetMealByID() {

        RecipeEntity expectedMeal = new RecipeEntity();
        RecipeEntity fetchedMeal;
        expectedMeal.setName(MEAL_NAME);

        expectedMeal = mealDAO.saveMeal(expectedMeal);
        fetchedMeal = mealDAO.getMealByID(expectedMeal.getId());

        Assertions.assertEquals(expectedMeal.getName(), fetchedMeal.getName());
        Assertions.assertEquals(expectedMeal.getId(), fetchedMeal.getId());

    }
}