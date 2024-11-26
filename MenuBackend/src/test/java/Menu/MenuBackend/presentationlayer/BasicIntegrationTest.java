package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.BaseIntegrationTest;
import Menu.MenuBackend.config.FirebaseAuthMockConfig;
import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@Transactional
@Import({ FirebaseAuthMockConfig.class})
public class BasicIntegrationTest extends BaseIntegrationTest {

    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected UserService userService;

    @Autowired
    public FirebaseAuth firebaseAuth;

    @AfterEach
    void afterEach() {
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
        }
    }


}
