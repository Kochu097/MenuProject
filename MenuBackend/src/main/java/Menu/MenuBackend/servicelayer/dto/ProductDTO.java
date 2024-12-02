package Menu.MenuBackend.servicelayer.dto;

import lombok.Data;
import Menu.MenuBackend.datalayer.enums.WeightUnit;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private UserDTO user;
    private String description;
    private String imageUrl;
    private Long weight;
    private WeightUnit weightUnit;
    private Long calories;
    private String source;
    private Boolean shared;
}

