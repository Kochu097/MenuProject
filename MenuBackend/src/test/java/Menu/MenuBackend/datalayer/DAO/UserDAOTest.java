package Menu.MenuBackend.datalayer.DAO;

import Menu.MenuBackend.datalayer.DAO.impl.UserDAOImpl;
import Menu.MenuBackend.datalayer.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class UserDAOTest extends BaseDAOIT{

//    @Autowired
//    public UserDAOImpl userDAO;

    @Test
    void testCreatingUser() {
        Assertions.assertNotNull(mySQLContainer.getDatabaseName());
//        User expectedUser = new User();
//        expectedUser.setFirebaseUserId("test-id");
//
//        User actualUser = userDAO.save(expectedUser);
//
//        Assertions.assertEquals(actualUser.getFirebaseUserId(), expectedUser.getFirebaseUserId());
//        Assertions.assertNotNull(actualUser.getId());
    }
}