package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.common.exception.UserNotFoundException;
import Menu.MenuBackend.presentationlayer.config.FirebaseAuthMockConfig;
import Menu.MenuBackend.presentationlayer.config.SecurityTestConfig;
import Menu.MenuBackend.servicelayer.UserService;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import com.google.firebase.ErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
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

import java.util.HashMap;
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
class UserCreationIT extends BasicIT{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirebaseAuth firebaseAuth;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserDTO mockUser = new UserDTO();
        mockUser.setFirebaseUserId(VALID_TOKEN);
        when(userService.getUserByAuthenticationToken(VALID_TOKEN)).thenReturn(mockUser);
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
        String newUserToken = VALID_TOKEN;
        FirebaseToken mockFirebaseToken = mock(FirebaseToken.class);
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", "new-user-123");
        when(mockFirebaseToken.getClaims()).thenReturn(claims);
        when(firebaseAuth.verifyIdToken(newUserToken)).thenReturn(mockFirebaseToken);

        // Simulate user not found
        when(userService.getUserByAuthenticationToken("new-user-123"))
                .thenThrow(new UserNotFoundException("User not found"));

        // Mock user creation
        UserDTO newUser = new UserDTO();
        newUser.setFirebaseUserId(newUserToken);
        when(userService.createUser(any(UserDTO.class))).thenReturn(newUser);

        mockMvc.perform(get("/api/test")
                        .header("Authorization", BEARER_PREFIX + newUserToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Should handle Firebase service exception")
    void testFirebaseServiceException() throws Exception {
        when(firebaseAuth.verifyIdToken(VALID_TOKEN))
                .thenThrow(new FirebaseAuthException(ErrorCode.ABORTED, "Firebase service error", null, null, null));

        mockMvc.perform(get("/api/test")
                        .header("Authorization", BEARER_PREFIX + VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}