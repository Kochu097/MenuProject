package Menu.MenuBackend.presentationlayer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final FireBaseSecurityFilter fireBaseSecurityFilter;
    private static final String[] WHITELISTED_API_ENDPOINTS = { "/api/test" };

    public SecurityConfig(FireBaseSecurityFilter fireBaseSecurityFilter) {
        this.fireBaseSecurityFilter = fireBaseSecurityFilter;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( authManager -> {
            authManager.requestMatchers(HttpMethod.GET, WHITELISTED_API_ENDPOINTS)
                    .permitAll()
                    .anyRequest().authenticated();
        }).addFilterBefore(fireBaseSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
