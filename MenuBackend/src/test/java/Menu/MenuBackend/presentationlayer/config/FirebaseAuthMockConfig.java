package Menu.MenuBackend.presentationlayer.config;

import com.google.firebase.ErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class FirebaseAuthMockConfig {

    public static final String VALID_TOKEN = "valid-token";
    public static final String INVALID_TOKEN = "invalid-token";

    @Bean
    @Primary
    public FirebaseAuth mockFirebaseAuth() throws FirebaseAuthException {
        // Create a full mock of FirebaseAuth
        FirebaseAuth mockFirebaseAuth = Mockito.mock(FirebaseAuth.class);

        // Stub the verifyIdToken method
        Mockito.lenient().when(mockFirebaseAuth.verifyIdToken(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String token = invocation.getArgument(0);

                    // Different behavior for different test tokens
                    if (VALID_TOKEN.equals(token)) {
                        return createMockFirebaseToken("user123");
                    }

                    throw new FirebaseAuthException(ErrorCode.PERMISSION_DENIED, "Invalid token", null, null, null);
                });

        return mockFirebaseAuth;
    }

    // Helper method to create a mock FirebaseToken
    private FirebaseToken createMockFirebaseToken(String uid) {
        FirebaseToken mockToken = Mockito.mock(FirebaseToken.class);

        // Configure basic token claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", uid);
        claims.put("email", uid + "@example.com");

        // Stub token methods
        Mockito.lenient().when(mockToken.getUid()).thenReturn(uid);
        Mockito.lenient().when(mockToken.getClaims()).thenReturn(claims);

        // Additional token validations if needed

        return mockToken;
    }
}
