package Menu.MenuBackend.datalayer.DAO;


import Menu.MenuBackend.datalayer.DAO.container.BaseMySQLContainer;
import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.modelmapper.internal.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(FirebaseAuthMockConfig.class)
@Testcontainers
public class BaseDAOIT {

    @Container
    public static MySQLContainer<BaseMySQLContainer> mySQLContainer = BaseMySQLContainer.getInstance();

    @BeforeAll
    public static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    public static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    public void beforeEach() {
        beforeEachTest();
    }

    @AfterEach
    public void afterEach() {
        afterEachTest();
    }

    public void beforeEachTest() {

    }

    public void afterEachTest() {

    }

    public User createUser(String firebaseUserId) {
        User user = new User();
        user.setFirebaseUserId(firebaseUserId);
//        entityManager.persist(user);
        return user;
    }


}
