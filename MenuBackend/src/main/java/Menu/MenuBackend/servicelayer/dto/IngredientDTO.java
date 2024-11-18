package Menu.MenuBackend.servicelayer.dto;

import lombok.Data;

@Data
public class IngredientDTO {
    private Integer id;
    private MenuDTO menu;
    private RecipeDTO recipe;
    private ProductDTO product;
}