package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.config.TestApplicationConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecurityIntegrationTest extends BasicIntegrationTest {

    @Autowired
    public FirebaseAuth firebaseAuth;

    @Test
    @DisplayName("Should verify valid token")
    void testValidTokenVerification() throws Exception {
        // Arrange
        String validToken = TestApplicationConfig.VALID_TOKEN;

        // Act
        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(validToken);

        // Assert
        assertThat(verifiedToken.getUid()).isEqualTo("user123T" + validToken);
        assertThat(verifiedToken.getClaims())
                .containsEntry("user_id", "user123T" + validToken);
    }

    @Test
    @DisplayName("Should throw exception for invalid token")
    void testInvalidTokenVerification() {
        // Arrange
        String invalidToken = TestApplicationConfig.INVALID_TOKEN;

        // Act & Assert
        assertThatThrownBy(() -> firebaseAuth.verifyIdToken(invalidToken))
                .isInstanceOf(FirebaseAuthException.class)
                .hasMessageContaining("Invalid token");
    }
}