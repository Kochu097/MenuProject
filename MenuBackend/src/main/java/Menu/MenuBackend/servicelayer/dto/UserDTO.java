package Menu.MenuBackend.servicelayer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "Authentication token is required")
    private String authenticationToken;
}