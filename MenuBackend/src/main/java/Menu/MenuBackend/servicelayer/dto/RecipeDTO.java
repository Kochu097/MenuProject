package Menu.MenuBackend.servicelayer.dto;

import Menu.MenuBackend.datalayer.enums.Difficulty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Data
public class RecipeDTO {
    private Integer id;
    private String name;
    private UserDTO user;
    private String description;
    private String imageUrl;
    private LocalTime preparationTime;
    private BigDecimal servings;
    private Difficulty difficulty;
    private List<IngredientDTO> ingredients;
    private Boolean shared;
}