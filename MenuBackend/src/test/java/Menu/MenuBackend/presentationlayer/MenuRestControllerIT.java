package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig;
import Menu.MenuBackend.presentationlayer.config.SecurityTestConfig;
import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.UserService;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig.VALID_TOKEN;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({SecurityTestConfig.class, FirebaseAuthMockConfig.class})
class MenuRestControllerIT extends BasicIT{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuService menuService;

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;


    @BeforeEach
    void setUp() {
        // Mock user service
//        EntityManager entityManager = entityManagerFactory.createEntityManager();

        User user = new User();
        user.setFirebaseUserId("user123");
//        entityManager.getTransaction().begin();
//        entityManager.persist(user);
//        entityManager.flush();
//        entityManager.getTransaction().commit();
    }

    @Test
    @DisplayName("Should fetch valid menu for given period")
    void testFetchingMenuForGivenPeriod() throws Exception {

        String startDate = LocalDate.of(2024,11,10).format(DateTimeFormatter.ISO_DATE);
        String endDate = LocalDate.of(2024,11,15).format(DateTimeFormatter.ISO_DATE);
        mockMvc.perform(get("/api/getMenuForPeriod")
                        .header("Authorization", BEARER_PREFIX + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate)
                        .param("endDate", endDate))
                .andExpect(status().isOk())
                .andDo(result -> {
                    List<MenuDTO> menu = objectMapper.readValue(result.getResponse().getContentAsString(), ArrayList.class);
                });
    }


}