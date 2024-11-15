package menu.menubackend.datalayer.dao.impl;

import menu.menubackend.datalayer.entity.Menu;
import java.util.Optional;
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


    @Autowired
    private MenuDAOImpl menuDAO;

    @Test
    void testSaveMenu() {

        Menu expectedMenu = new Menu();
        expectedMenu.setDay(NOW);

        Menu savedMenu = menuDAO.save(expectedMenu);

        Assertions.assertEquals(NOW, savedMenu.getDay());

    }

    @Test
    void testUpdateMenu() {

        Menu expectedMenu = new Menu();
        expectedMenu.setDay(NOW);

        Menu savedMenu = menuDAO.save(expectedMenu);

        Menu updatedMenu = new Menu();
        updatedMenu.setId(savedMenu.getId());
        updatedMenu.setDay(NOW);

        updatedMenu = menuDAO.save(updatedMenu);

        Assertions.assertEquals(savedMenu.getId(), updatedMenu.getId());
        Assertions.assertEquals(NOW, updatedMenu.getDay());
    }

    @Test
    void testGetMenuById() {

        Menu expectedMenu = new Menu();
        expectedMenu.setDay(NOW);

        Menu savedMenu = menuDAO.save(expectedMenu);

        Optional<Menu> fetchedMenu = menuDAO.findById(savedMenu.getId());

        Assertions.assertTrue(fetchedMenu.isPresent());

        Assertions.assertEquals(expectedMenu, fetchedMenu.get());
    }
}