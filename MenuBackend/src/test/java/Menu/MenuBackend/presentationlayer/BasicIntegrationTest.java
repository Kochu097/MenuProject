package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.BaseIntegrationTest;
import Menu.MenuBackend.servicelayer.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class BasicIntegrationTest extends BaseIntegrationTest {

    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserService userService;

    @AfterEach
    void afterEach() {
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
        }
    }


}
