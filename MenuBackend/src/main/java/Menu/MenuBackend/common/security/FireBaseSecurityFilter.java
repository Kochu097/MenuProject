package Menu.MenuBackend.common.security;

import Menu.MenuBackend.common.exception.UserNotFoundException;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import Menu.MenuBackend.servicelayer.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class FireBaseSecurityFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_CLAIM = "user_id";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.replace(BEARER_PREFIX, "");
            Optional<String> userId = getUserIdFromToken(token);
            if(userId.isPresent()) {
                UserDTO userDTO = fetchOrCreateUser(userId.get());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDTO, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                setAuthErrorDetails(response);
            }
        } else {
            setAuthErrorDetails(response);
        }
    }

    private UserDTO fetchOrCreateUser(String token) {
        try {
            return userService.getUserByAuthenticationToken(token);
        } catch (UserNotFoundException e ) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFirebaseUserId(token);
            return userService.createUser(userDTO);
        }
    }

    private Optional<String> getUserIdFromToken(String token) {
        try{
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token);
            String userId = String.valueOf(firebaseToken.getClaims().get(USER_ID_CLAIM));
            return Optional.of(userId);
        } catch (FirebaseAuthException e) {
            return Optional.empty();
        }
    }

    private void setAuthErrorDetails(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                "Authentication failure: Token missing, invalid or expired");
        response.getWriter().write(new ObjectMapper().writeValueAsString(problemDetail));
    }
}
