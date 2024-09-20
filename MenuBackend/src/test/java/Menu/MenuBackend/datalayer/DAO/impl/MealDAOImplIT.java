package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.entity.MealEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class MealDAOImplIT {

    @Autowired
    private MealDAOImpl mealDAO;

    private String MEAL_NAME = "Spaghetti";

    @Test
    public void testSaveMeal() {

        MealEntity meal = new MealEntity();
        meal.setName(MEAL_NAME);

        meal = mealDAO.saveMeal(meal);

        Assertions.assertEquals(MEAL_NAME, meal.getName());
        Assertions.assertNotNull(meal.getId());

    }

    @Test
    public void testGetMealByID() {

        MealEntity expectedMeal = new MealEntity();
        MealEntity fetchedMeal;
        expectedMeal.setName(MEAL_NAME);

        expectedMeal = mealDAO.saveMeal(expectedMeal);
        fetchedMeal = mealDAO.getMealByID(expectedMeal.getId());

        Assertions.assertEquals(expectedMeal.getName(), fetchedMeal.getName());
        Assertions.assertEquals(expectedMeal.getId(), fetchedMeal.getId());

    }
}