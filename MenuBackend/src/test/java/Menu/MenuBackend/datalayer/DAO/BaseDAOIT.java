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
@Import(FirebaseAuthMockConfig.class)
@Testcontainers
public class BaseDAOIT {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("menu_database")
            .withUsername("root")
            .withPassword("zaq1@WSX");

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
        System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
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
