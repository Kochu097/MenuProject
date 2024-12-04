package Menu.MenuBackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestApplicationConfig {

    public static final String VALID_TOKEN = "valid-token";
    public static final String INVALID_TOKEN = "invalid-token";

    @Bean
    @Primary
    public FirebaseAuth mockFirebaseAuth() throws FirebaseAuthException {
        FirebaseAuth mockFirebaseAuth = Mockito.mock(FirebaseAuth.class);

        GoogleCredentials mockGoogleCredentials = Mockito.mock(GoogleCredentials.class);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(mockGoogleCredentials)
                .build();

        if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        // Stub the verifyIdToken method
        Mockito.lenient().when(mockFirebaseAuth.verifyIdToken(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String token = invocation.getArgument(0);

                    // Different behavior for different test tokens
                    if (token.startsWith("valid-token")) {
                        return createMockFirebaseToken("user123T" + token);
                    }

                    throw new FirebaseAuthException(ErrorCode.PERMISSION_DENIED, "Invalid token", null, null, null);
                });

        return mockFirebaseAuth;
    }

    @Bean
    @Primary
    public ModelMapper mockModelMapper() throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
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