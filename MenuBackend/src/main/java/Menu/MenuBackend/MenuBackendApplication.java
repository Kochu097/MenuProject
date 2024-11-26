package Menu.MenuBackend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MenuBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(MenuBackendApplication.class, args);
	}

}
