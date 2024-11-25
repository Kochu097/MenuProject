package Menu.MenuBackend.datalayer.DAO;


import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Import(FirebaseAuthMockConfig.class)
@Testcontainers
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseDAOTest {

    @Container
    protected static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.30")
            .withDatabaseName("menu_database")
            .withUsername("root")
            .withPassword("zaq1@WSX")
            .withReuse(true);

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", MYSQL_CONTAINER::getDriverClassName);
    }

    @Autowired
    public EntityManager entityManager;

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @BeforeEach
    @Transactional
    public void beforeEach() {
        beforeEachTest();
    }

    @AfterEach
    @Transactional
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

    public User createUser(String firebaseUserId) {
        User user = new User();
        user.setFirebaseUserId(firebaseUserId);
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }


}
