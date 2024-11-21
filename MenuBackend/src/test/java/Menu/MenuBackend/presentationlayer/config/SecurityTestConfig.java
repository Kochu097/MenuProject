package Menu.MenuBackend.presentationlayer.config;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    public FirebaseAuth firebaseAuth() {
        return mock(FirebaseAuth.class);
    }
}
