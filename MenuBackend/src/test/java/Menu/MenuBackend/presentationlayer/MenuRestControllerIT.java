package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.common.exception.UserNotFoundException;
import Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig;
import Menu.MenuBackend.presentationlayer.config.SecurityTestConfig;
import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.UserService;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig.VALID_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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


    @BeforeEach
    void setUp() {
        // Mock user service
        UserDTO mockUser = new UserDTO();
        mockUser.setAuthenticationToken("user123");
        userService.createUser(mockUser);
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