package Menu.MenuBackend.datalayer.DAO;


import Menu.MenuBackend.BaseIntegrationTest;
import Menu.MenuBackend.datalayer.DAO.impl.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;

public abstract class BaseDAOIntegrationTest extends BaseIntegrationTest {

    @Autowired
    protected ProductDAOImpl productDAO;

    @Autowired
    protected MenuDAOImpl menuDAO;

    @Autowired
    protected UserDAOImpl userDAO;

    @Autowired
    protected IngredientDAOImpl menuItemDAO;

    @Autowired
    protected RecipeDAOImpl recipeDAO;

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @BeforeEach
    public void beforeEach() {
        beforeEachTest();
    }

    @AfterEach
    public void afterEach() {
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
        }
        afterEachTest();
    }

    public void beforeEachTest() {

    }

    public void afterEachTest() {

    }


}
