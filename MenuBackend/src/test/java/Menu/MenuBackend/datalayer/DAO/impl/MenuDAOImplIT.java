package Menu.MenuBackend.datalayer.DAO.impl;

import Menu.MenuBackend.datalayer.entity.MenuEntity;
import Menu.MenuBackend.datalayer.enums.MealType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class MenuDAOImplIT {

    private static final LocalDate NOW = LocalDate.now();
    private static final MealType MEAL_TYPE = MealType.BREAKFAST;
    private static final MealType UPDATED_MEAL_TYPE = MealType.SUPPER;


    @Autowired
    private MenuDAOImpl menuDAO;

    @Test
    void testSaveMenu() {

        MenuEntity expectedMenuEntity = new MenuEntity();
        expectedMenuEntity.setDay(NOW);
        expectedMenuEntity.setMealType(MEAL_TYPE);

        MenuEntity savedMenuEntity = menuDAO.saveMenu(expectedMenuEntity);

        Assertions.assertEquals(NOW, savedMenuEntity.getDay());
        Assertions.assertEquals(MEAL_TYPE, savedMenuEntity.getMealType());

    }

    @Test
    void testUpdateMenu() {

        MenuEntity expectedMenuEntity = new MenuEntity();
        expectedMenuEntity.setDay(NOW);
        expectedMenuEntity.setMealType(MEAL_TYPE);

        MenuEntity savedMenuEntity = menuDAO.saveMenu(expectedMenuEntity);

        MenuEntity updatedMenuEntity = new MenuEntity();
        updatedMenuEntity.setId(savedMenuEntity.getId());
        updatedMenuEntity.setDay(NOW);
        updatedMenuEntity.setMealType(UPDATED_MEAL_TYPE);

        updatedMenuEntity = menuDAO.updateMenu(updatedMenuEntity);

        Assertions.assertEquals(savedMenuEntity.getId(), updatedMenuEntity.getId());
        Assertions.assertEquals(UPDATED_MEAL_TYPE, updatedMenuEntity.getMealType());
        Assertions.assertEquals(NOW, updatedMenuEntity.getDay());
    }

    @Test
    void testGetMenuById() {

        MenuEntity expectedMenuEntity = new MenuEntity();
        expectedMenuEntity.setDay(NOW);
        expectedMenuEntity.setMealType(MEAL_TYPE);

        MenuEntity savedMenuEntity = menuDAO.saveMenu(expectedMenuEntity);

        MenuEntity fetchedMenuEntity = menuDAO.getMenuById(savedMenuEntity.getId());

        Assertions.assertEquals(expectedMenuEntity, fetchedMenuEntity);
    }
}