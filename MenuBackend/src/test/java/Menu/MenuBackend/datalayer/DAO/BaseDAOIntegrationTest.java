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
    protected IngredientDAOImpl ingredientDAO;

    @Autowired
    protected RecipeDAOImpl recipeDAO;

    @BeforeEach
    public void beforeEach() {
        beforeEachTest();
    }

    @AfterEach
    public void afterEach() {
        afterEachTest();
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
        }
    }

    public void beforeEachTest() {

    }

    public void afterEachTest() {

    }


}
