package Menu.MenuBackend.servicelayer.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class RecipeDTO {
    private String name;
    private UserDTO user;
    private String description;
    private String imageUrl;
    private LocalTime preparationTime;
    private BigDecimal servings;
    private String difficulty;
}