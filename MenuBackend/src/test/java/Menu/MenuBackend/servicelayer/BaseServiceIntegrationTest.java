package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.BaseIntegrationTest;
import Menu.MenuBackend.datalayer.DAO.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;

public class BaseServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserDAO userDAO;

    @AfterEach
    void afterEach() {
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
        }
    }
}
