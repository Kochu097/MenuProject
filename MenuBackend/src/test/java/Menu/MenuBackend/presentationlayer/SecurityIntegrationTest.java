package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.config.FirebaseAuthMockConfig;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FirebaseAuthMockConfig.class)
class SecurityIntegrationTest extends BasicIntegrationTest {

    @Test
    @DisplayName("Should verify valid token")
    void testValidTokenVerification() throws FirebaseAuthException {
        // Arrange
        String validToken = FirebaseAuthMockConfig.VALID_TOKEN;

        // Act
        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(validToken);

        // Assert
        assertThat(verifiedToken.getUid()).isEqualTo("user123T"+validToken);
        assertThat(verifiedToken.getClaims())
                .containsEntry("user_id", "user123T"+validToken);
    }

    @Test
    @DisplayName("Should throw exception for invalid token")
    void testInvalidTokenVerification() {
        // Arrange
        String invalidToken = FirebaseAuthMockConfig.INVALID_TOKEN;

        // Act & Assert
        assertThatThrownBy(() -> firebaseAuth.verifyIdToken(invalidToken))
                .isInstanceOf(FirebaseAuthException.class)
                .hasMessageContaining("Invalid token");
    }
}