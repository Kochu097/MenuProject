package menu.menubackend.servicelayer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;

    @NotBlank(message = "Authentication token is required")
    private String authenticationToken;
}