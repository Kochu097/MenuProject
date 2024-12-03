package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static Menu.MenuBackend.config.TestApplicationConfig.INVALID_TOKEN;
import static Menu.MenuBackend.config.TestApplicationConfig.VALID_TOKEN;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class UserCreationIntegrationTest extends BasicIntegrationTest {

    @BeforeEach
    void setUp() {
        UserDTO user = new UserDTO();
        user.setFirebaseUserId(VALID_TOKEN);
        userService.createUser(user);
    }

    @Test
    @DisplayName("Should allow access with valid token")
    void testValidTokenAccess() throws Exception {

        mockMvc.perform(get("/api/test")
                        .header("Authorization", BEARER_PREFIX + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should deny access with invalid token")
    void testInvalidTokenAccess() throws Exception {
        String invalidToken = "invalid-token";

        mockMvc.perform(get("/api/test")
                        .header("Authorization", BEARER_PREFIX + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Authentication failure: Token missing, invalid or expired"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should deny access with missing token")
    void testMissingTokenAccess() throws Exception {
        mockMvc.perform(get("/api/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Authentication failure: Token missing, invalid or expired"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should create new user when not found")
    void testCreateNewUserWhenNotFound() throws Exception {
        String newUserToken = VALID_TOKEN+"2";

        mockMvc.perform(get("/api/test")
                        .header("Authorization", BEARER_PREFIX + newUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        List<UserDTO> users = userService.getAllUsers();

        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(2, users.size());
        Assertions.assertTrue(users.stream().anyMatch(u-> u.getFirebaseUserId().contains(newUserToken)));
    }

    @Test
    @DisplayName("Should handle Firebase service exception")
    void testFirebaseServiceException() throws Exception {

        mockMvc.perform(get("/api/test")
                        .header("Authorization", BEARER_PREFIX + INVALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}