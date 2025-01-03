package Menu.MenuBackend.servicelayer.dto;

import Menu.MenuBackend.datalayer.enums.AmountUnit;
import lombok.Data;

@Data
public class IngredientDTO {
    private Integer id;
    private RecipeDTO recipe;
    private ProductDTO product;
    private Long amount;
    private AmountUnit amountUnit;
}