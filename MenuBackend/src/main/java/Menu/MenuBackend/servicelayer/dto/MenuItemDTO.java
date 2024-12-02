package Menu.MenuBackend.servicelayer.dto;

import Menu.MenuBackend.datalayer.enums.MenuItemType;
import lombok.Data;

@Data
public class MenuItemDTO {
    private Integer id;
    private MenuItemType menuItemType;
    private RecipeDTO recipe;
    private ProductDTO product;

}
